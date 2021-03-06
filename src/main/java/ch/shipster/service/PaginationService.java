package ch.shipster.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Timo

@Service
public class PaginationService {

    public static <O> List<O> paginate(List<O> inList, int page, int perPage) throws Exception {
        List<O> outList = new ArrayList<O>();

        if (inList.isEmpty()) {
            ShipsterLogger.logger.error("List is empty");
            throw new Exception("List is empty");
        }

        if (inList.size() < (page - 1) * perPage) {
            ShipsterLogger.logger.error("Page to high. Only" + inList.size() + "items in List");
            throw new Exception("Page to high. Only" + inList.size() + "items in List");
        }

        if (page <= 0) {
            ShipsterLogger.logger.error("Page must be 1 or higher");
            throw new Exception("Page must be 1 or higher");
        }

        int startIndex = (page - 1) * perPage - 1;
        int endIndex = page * perPage - 1;

        for (int i = startIndex; i <= endIndex; i++) {
            outList.add(inList.get(i));
        }

        return outList;
    }
}
