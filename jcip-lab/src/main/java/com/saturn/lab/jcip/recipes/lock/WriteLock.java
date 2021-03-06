package com.saturn.lab.jcip.recipes.lock;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;

/**
 * 类比继承权
 */
public class WriteLock extends ProtocolSupport {
    private static final Logger LOG = LoggerFactory.getLogger(WriteLock.class);

    private final String dir;
    private String id;
    private ZNodeName idName;
    private String ownerId;
    private String lastChildId;
    private byte[] data = {0x12, 0x34};
    private LockListener callback;
    private LockZooKeeperOperation zop;

    private CountDownLatch waitLatch=new CountDownLatch(1);

    /**
     * zookeeper contructor for writelock
     *
     * @param zookeeper zookeeper client instance
     * @param dir       the parent path you want to use for locking
     * @param acl       the acls that you want to use for all the paths,
     *                  if null world read/write is used.
     */
    public WriteLock(ZooKeeper zookeeper, String dir, List<ACL> acl) {
        super(zookeeper);
        this.dir = dir;
        if (acl != null) {
            setAcl(acl);
        }
        this.zop = new LockZooKeeperOperation();
    }

    /**
     * zookeeper contructor for writelock with callback
     *
     * @param zookeeper the zookeeper client instance
     * @param dir       the parent path you want to use for locking
     * @param acl       the acls that you want to use for all the paths
     * @param callback  the call back instance
     */
    public WriteLock(ZooKeeper zookeeper, String dir, List<ACL> acl,
                     LockListener callback) {
        this(zookeeper, dir, acl);
        this.callback = callback;
    }

    /**
     * return the current locklistener
     *
     * @return the locklistener
     */
    public LockListener getLockListener() {
        return this.callback;
    }

    /**
     * register a different call back listener
     *
     * @param callback the call back instance
     */
    public void setLockListener(LockListener callback) {
        this.callback = callback;
    }

    /**
     * Removes the lock or associated znode if
     * you no longer require the lock. this also
     * removes your request in the queue for locking
     * in case you do not already hold the lock.
     *
     * @throws RuntimeException throws a runtime exception
     *                          if it cannot connect to zookeeper.
     */
    public synchronized void unlock() throws RuntimeException {

        if (!isClosed() && id != null) {
            // we don't need to retry this operation in the case of failure
            // as ZK will remove ephemeral files and we don't wanna hang
            // this process when closing if we cannot reconnect to ZK
            try {

                ZooKeeperOperation zopdel = new ZooKeeperOperation() {
                    public boolean execute() throws KeeperException,
                            InterruptedException {
                        zookeeper.delete(id, -1);

                        System.out.println(new Date() + " tid:" + Thread.currentThread().getId() + " deleted node:" + id);
                        return Boolean.TRUE;
                    }
                };
                zopdel.execute();
            } catch (InterruptedException e) {
                LOG.warn("Caught: " + e, e);
                //set that we have been interrupted.
                Thread.currentThread().interrupt();
            } catch (KeeperException.NoNodeException e) {
                // do nothing
            } catch (KeeperException e) {
                LOG.warn("Caught: " + e, e);
                throw (RuntimeException) new RuntimeException(e.getMessage()).
                        initCause(e);
            } finally {
                if (callback != null) {
                    callback.lockReleased();
                }
                id = null;
            }
        }
    }

    /**
     * the watcher called on
     * getting watch while watching
     * my predecessor
     */
    private class LockWatcher implements Watcher {
        public void process(WatchedEvent event) {
            // lets either become the leader or watch the new/updated node
            LOG.debug("Watcher fired on path: " + event.getPath() + " state: " +
                    event.getState() + " type " + event.getType());

            System.out.println(new Date() + " tid: " + Thread.currentThread().getId() +
                    " Watcher fired on path: " + event.getPath() + " state: " +
                    event.getState() + " type " + event.getType());
            try {
               // lock();

//                if(event.getType()== Event.EventType.NodeDeleted)
//                {
//                    //notifyAll();
//                    waitLatch.countDown();
//                }
                waitLatch.countDown();

            } catch (Exception e) {
                LOG.warn("Failed to acquire lock: " + e, e);
            }
        }
    }

    /**
     * a zoookeeper operation that is mainly responsible
     * for all the magic required for locking.
     */
    private class LockZooKeeperOperation implements ZooKeeperOperation {

        /**
         * find if we have been created earler if not create our node
         *
         * @param prefix    the prefix node
         * @param zookeeper teh zookeeper client
         * @param dir       the dir paretn
         * @throws KeeperException
         * @throws InterruptedException
         */
        private void findPrefixInChildren(String prefix, ZooKeeper zookeeper, String dir)
                throws KeeperException, InterruptedException {
            List<String> names = zookeeper.getChildren(dir, false);
            for (String name : names) {
                if (name.startsWith(prefix)) {
                    id = name;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Found id created last time: " + id);
                    }

                    System.out.println("Found id created last time: " + id);
                    break;
                }
            }
            if (id == null) {
                id = zookeeper.create(dir + "/" + prefix, data,
                        getAcl(), EPHEMERAL_SEQUENTIAL);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Created id: " + id);
                }
                System.out.println("Created id: " + id);
            }

        }

        /**
         * the command that is run and retried for actually
         * obtaining the lock
         *
         * @return if the command was successful or not
         */
        public boolean execute() throws KeeperException, InterruptedException {

            boolean getLock=false;

            do {
                if (id == null) {
                    long sessionId = zookeeper.getSessionId();
                    String prefix = "x-" + sessionId + "-";
                    // lets try look up the current ID if we failed
                    // in the middle of creating the znode
                    findPrefixInChildren(prefix, zookeeper, dir);
                    idName = new ZNodeName(id);
                }
                if (id != null) {
                    List<String> names = zookeeper.getChildren(dir, false);
                    if (names.isEmpty()) {
                        //不正常的,说明上次创建过程中异常
                        //至少得有一个node
                        LOG.warn("No children in: " + dir + " when we've just " +
                                "created one! Lets recreate it...");
                        // lets force the recreation of the id
                        id = null;
                    } else {
                        //就看前一个
                        // lets sort them explicitly (though they do seem to come back in order ususally :)
                        SortedSet<ZNodeName> sortedNames = new TreeSet<ZNodeName>();
                        for (String name : names) {
                            sortedNames.add(new ZNodeName(dir + "/" + name));
                        }

                        //最小sequence 的node id
                        ownerId = sortedNames.first().getName();
                        //比当前创建node 小的所有的,不包含当前的(exclusive)
                        SortedSet<ZNodeName> lessThanMe = sortedNames.headSet(idName);
                        if (!lessThanMe.isEmpty()) {
                            //比自己小的前一位
                            ZNodeName lastChildName = lessThanMe.last();
                            lastChildId = lastChildName.getName();
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("watching less than me node: " + lastChildId);
                            }

                            //key 监视比自己小的前一位:lastChildId
                            // 等待前辈退位
                            Stat stat = zookeeper.exists(lastChildId, new LockWatcher());
                            if (stat != null) {
                               // wait();//wait
                                System.out.println("await");
                                waitLatch.await();

                                if(!isOwner())
                                {
                                  //  id=null;//retry
                                    System.out.println("retry");
                                    continue;
                                }

                                return Boolean.FALSE;

                            } else {
                                LOG.warn("Could not find the" +
                                        " stats for less than me: " + lastChildName.getName());
                            }
                        } else {

                            //如果没有前一个，自己就是owner,就是leader,世界之王
                            if (isOwner()) {
                                if (callback != null) {
                                    callback.lockAcquired();
                                }

                              //  waitLatch.countDown();
                                return Boolean.TRUE;
                            }
                        }
                    }
                }
            }
            while (id == null  ||!getLock);
            return Boolean.FALSE;
        }
    }

    ;

    /**
     * Attempts to acquire the exclusive write lock returning whether or not it was
     * acquired. Note that the exclusive lock may be acquired some time later after
     * this method has been invoked due to the current lock owner going away.
     */
    public synchronized boolean lock() throws KeeperException, InterruptedException {
        if (isClosed()) {
            return false;
        }
        ensurePathExists(dir);

        return (Boolean) retryOperation(zop);
    }

    /**
     * return the parent dir for lock
     *
     * @return the parent dir used for locks.
     */
    public String getDir() {
        return dir;
    }

    /**
     * Returns true if this node is the owner of the
     * lock (or the leader)
     */
    public boolean isOwner() {
        return id != null && ownerId != null && id.equals(ownerId);
    }

    /**
     * return the id for this lock
     *
     * @return the id for this lock
     */
    public String getId() {
        return this.id;
    }
}
