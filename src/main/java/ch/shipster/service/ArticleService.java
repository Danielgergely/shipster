package ch.shipster.service;

import ch.shipster.data.domain.Article;
import ch.shipster.data.repository.ArticleRepository;
import ch.shipster.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

//Timo

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public List<Article> getAllArticles() {
        List<Article> articles = articleRepository.findAll();
        articles.sort(Comparator.comparing(Article::getId));
        return articles;
    }

    public List<Article> filterArticlesByPrize(float min, float max) {
        List<Article> outList = new ArrayList<>();
        List<Article> fullList = articleRepository.findAll();

        for (Article a : fullList) {
            if (a.getPrice() > min && a.getPrice() < max) {
                outList.add(a);
            }
        }
        outList.sort(Comparator.comparing(Article::getId));
        return outList;
    }

    public Article saveArticle(Long articleId,
                               String name,
                               String description,
                               String imageUrl,
                               Float price,
                               Float palletSpace,
                               Float maxStack) {
        Optional<Long> inId = Optional.ofNullable(articleId);
        Optional<String> inName = Optional.ofNullable(name);
        Optional<String> inDescription = Optional.ofNullable(description);
        Optional<String> inImageUrl = Optional.ofNullable(imageUrl);
        Optional<Float> inPrice = Optional.ofNullable(price);
        Optional<Float> inPalletSpace = Optional.ofNullable(palletSpace);
        Optional<Float> inMaxStack = Optional.ofNullable(maxStack);

        Article outArticle;

        if (inId.isEmpty()) {
            outArticle = new Article();
        } else if (articleRepository.existsById(articleId)) {
            outArticle = articleRepository.getById(articleId);
        } else {
            outArticle = new Article();
        }

        if (inName.isPresent()) outArticle.setName(name);
        if (inDescription.isPresent()) outArticle.setDescription(description);
        if (inImageUrl.isPresent()) outArticle.setImageUrl(imageUrl);
        if (inPrice.isPresent()) outArticle.setPrice(price);
        if (inPalletSpace.isPresent()) outArticle.setPalletSpace(palletSpace);
        if (inMaxStack.isPresent()) outArticle.setMaxStack(maxStack);

        return saveArticle(outArticle);
    }

    public Article saveArticle(Article article) {
        if (articleRepository.existsById(article.getId())) {
            return articleRepository.save(article);
        } else {
            return saveArticle(null,
                    article.getName(),
                    article.getDescription(),
                    article.getUrl(),
                    article.getPrice(),
                    article.getPalletSpace(),
                    article.getMaxStack());
        }

    }

    public Article findById(Long articleId) {
        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isEmpty()) {
            ShipsterLogger.logger.error("No article found for id: " + articleId);
            throw new NotFoundException("No article found for id: " + articleId);
        }
        return article.get();
    }


}
