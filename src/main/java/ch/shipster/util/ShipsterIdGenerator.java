package ch.shipster.util;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.*;
import java.util.stream.*;

public class ShipsterIdGenerator implements IdentifierGenerator, Configurable {
    private String prefix;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {

        String query = String.format("select %s from %s",
                session.getEntityPersister(obj.getClass().getName(), obj)
                        .getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        List ids = session.createQuery(query).stream().toList();
        Iterator<String> iterator = ids.iterator();
        List<Long> idList = new ArrayList<>();
        while (iterator.hasNext()) {
            idList.add(Long.parseLong(iterator.next().replace(prefix + "_", "")));
        }
        Long max = idList.stream().mapToLong(i -> i).max().orElseThrow(NoSuchElementException::new);
        return prefix + "_" + (max + 1);
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
    }
}
