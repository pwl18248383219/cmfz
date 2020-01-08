package com.baizhi.pwl.service;

import com.baizhi.pwl.dao.ArticleDao;
import com.baizhi.pwl.entity.Article;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleDao articleDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Integer queryCount(Article article) {
        int count = articleDao.selectCount(article);
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Article> queryByPage(Article article, Integer page, Integer rows) {
        List<Article> articles = articleDao.selectByRowBounds(article, new RowBounds((page - 1) * rows, rows));
        return articles;
    }

    @Override
    public List<Article> queryAll() {
        List<Article> articles = articleDao.selectAll();
        return articles;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Article queryOne(String id) {
        Article article = new Article();
        article.setId(id);
        Article article1 = articleDao.selectOne(article);
        return article1;
    }

    @Override
    public void add(Article article) {
        String uuid = UUID.randomUUID().toString();
        article.setId(uuid);
        article.setCreateDate(new Date());
        article.setPublishDate(new Date());
        articleDao.insert(article);
    }

    @Override
    public void modify(Article article) {
        articleDao.updateByPrimaryKeySelective(article);
    }

    @Override
    public void remove(String[] id) {
        articleDao.deleteByIdList(Arrays.asList(id));
    }
}
