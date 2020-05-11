package cn.enn.wise.platform.mall;


import cn.enn.wise.platform.mall.util.MessageSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MqTest {
    @Autowired
    private MessageSender sender;

    @Test
    public void testMqDemo() {
    }
}
