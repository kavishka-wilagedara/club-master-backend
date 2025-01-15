package com.uokclubmanagement.controller;

import com.uokclubmanagement.entity.Event;
import com.uokclubmanagement.entity.News;
import com.uokclubmanagement.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @PostMapping("/{clubId}/save/{clubAdminId}")
    public News createNews(@PathVariable("clubId") String clubId, @PathVariable("clubAdminId") String clubAdminId, @RequestBody News news) {
        return newsService.createNews(news, clubId, clubAdminId);
    }

    @GetMapping("/all")
    public List<News> getAllEvents() {
        return newsService.getAllNews();
    }

    @PutMapping("/{clubAdminId}/update/{newsId}")
    public News updateNews(@PathVariable("clubAdminId") String clubAdminId, @PathVariable("newsId") String newsId, @RequestBody News news) {
        return newsService.updateNewsById(clubAdminId, newsId, news);
    }

    @DeleteMapping("/delete/{newsId}")
    public void deleteNews(@PathVariable("newsId") String newsId) {
        newsService.deleteNewsById(newsId);
    }

    @GetMapping("/getNews/{newsId}")
    public News getEvent(@PathVariable("newsId") String newsId) {
        return newsService.getNewsById(newsId);
    }

    @PostMapping("/{newsId}/addLike/{clubId}/{memberId}")
    public News addLike(@PathVariable("newsId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return newsService.addLikeToNews(newsId, clubId, memberId);
    }

    @PostMapping("/{newsId}/removeLike/{clubId}/{memberId}")
    public News removeLike(@PathVariable("newsId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return newsService.removeLikeFromNews(newsId, clubId, memberId);
    }

    @PostMapping("/{newsId}/likeCount/{clubId}/{memberId}")
    public int likeCount(@PathVariable("newsId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return newsService.getNewsLikeCount(newsId, clubId, memberId);
    }

    @PostMapping("/{newsId}/dislikeCount/{clubId}/{memberId}")
    public int dislikeCount(@PathVariable("newsId") String newsId, @PathVariable("clubId") String clubId, @PathVariable("memberId") String memberId) {
        return newsService.getNewsDislikeCount(newsId, clubId, memberId);
    }
}
