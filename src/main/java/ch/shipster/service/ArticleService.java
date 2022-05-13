package ch.shipster.service;

import ch.shipster.data.domain.Article;
import ch.shipster.data.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//Timo

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        List<Article> outList = articleRepository.findAll();
        return outList;
    }

    public List<Article> filterArticlesByPrize(float min, float max) {
        List<Article> outList = new ArrayList<>();
        List<Article> fullList = articleRepository.findAll();

        for (Article a : fullList) {
            if (a.getPrice() > min && a.getPrice() < max) {
                outList.add(a);
            }
        }

        return outList;
    }

}
