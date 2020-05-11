package cn.enn.wise.ssop.service.order.thirdparty.shenda;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Register implements CommandLineRunner{
	@Override
	public void run(String... args) {
		ShendaOrder ticketHeader = new ShendaOrder();
		Adapter.TICKET_MAP.put(5L, ticketHeader);
		Conf TicketConf = new ShendaConfig("sdzfxxzlymqlfx","xzlymqlfx","148DD2AE0EC8F38B0A4CF24DA130D2BC");
		Adapter.TICKET_CONF_MAP.put(5L, TicketConf);
	}
}