package com.mkyong.jms.processor;

import com.google.gson.Gson;
import com.mkyong.config.properties.ClientMessageProperties;
import com.mkyong.data.Developer;
import com.mkyong.data.Message;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Component
public class Test1Processor implements Processor {

	private static final Logger LOGGER = LogManager.getLogger(Test1Processor.class);

	private String[][]  DEVELOPERS = {
			{"Сергей", "Ветров"      , "Java Developer",  "6", "2400"},
			{"Олег"  , "Мантров"     , "C++ Developer" , "10", "2300"},
			{"Остап" , "Бендер"      , "C# Developer"  ,  "5", "2200"},
			{"Киса"  , "Воробьянинов", "PHP Developer" ,  "4", "2500"}};

	@Autowired
	private ClientMessageProperties clientMessageProperties;

	@Autowired
	@Qualifier("h2SessionFactory")
	private SessionFactory sessionFactory;

	@Override
	public void process(Exchange exchange) throws Exception {
//		LOGGER.error("getPattern =  {}", "'" + exchange.getPattern().toString() + "'");
//		LOGGER.error("getIn =  {}", "'" + exchange.getIn().toString() + "'");
//		LOGGER.error("getOut =  {}", "'" + exchange.getOut().toString() + "'");
//		LOGGER.error("getExchangeId =  {}", "'" + exchange.getExchangeId() + "'");
//		LOGGER.error("getFromRouteId =  {}", "'" + exchange.getFromRouteId() + "'");

		Object obj = exchange.getIn().getBody();
		if (obj != null) {
			String message = String.valueOf(obj); //Message message = new Gson().fromJson(String.valueOf(obj), Message.class);
			LOGGER.debug(" >>|  {}", message);

			Thread.sleep(clientMessageProperties.getResponseDelay());

			try {
				save(new Developer(DEVELOPERS[0][0], DEVELOPERS[0][1], DEVELOPERS[0][2], Integer.valueOf(DEVELOPERS[0][3]), Integer.valueOf(DEVELOPERS[0][4])));
				save(new Developer(DEVELOPERS[1][0], DEVELOPERS[1][1], DEVELOPERS[1][2], Integer.valueOf(DEVELOPERS[1][3]), Integer.valueOf(DEVELOPERS[1][4])));
				save(new Developer(DEVELOPERS[2][0], DEVELOPERS[2][1], DEVELOPERS[2][2], Integer.valueOf(DEVELOPERS[2][3]), Integer.valueOf(DEVELOPERS[2][4])));
				for (Developer developer: listDevelopers(1)) LOGGER.info("|<<   {}", developer);
			} catch (Throwable e) {
				LOGGER.error("|<<   {}", e.getLocalizedMessage()); // throw new ExceptionInInitializerError(e);
			}

//			message = new Gson().fromJson(String.valueOf(obj), Message.class);
			LOGGER.debug("|<<   {}", message);
			exchange.getIn().setBody(obj);
		}
	}

	/**
	 * @see https://coderoad.ru/23214454/org-hibernate-MappingException-неизвестная-сущность-annotations-Users
	 *      https://overcoder.net/q/1288149/транзакция-не-была-успешно-запущена-в-то-время-как-txcommit-окружен-условием-if
	 */
	private void save(Developer developer) {
		Session session = sessionFactory.openSession();

		Transaction transaction = session.beginTransaction();
		session.save(developer);
		transaction.commit();

		session.close();
	}

	private List<Developer> listDevelopers(final int minExperience) {
		Session session = sessionFactory.openSession();

		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Developer.class);
		criteria.add(Restrictions.gt("experience", minExperience));
		List<Developer> developers = criteria.list();
		transaction.commit();

		session.close();
		return developers;
	}
}
