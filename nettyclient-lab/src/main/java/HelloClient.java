import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Created by lyz on 2017/6/18.
 */
public class HelloClient {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
    }
}
