
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cartoons")
public class Cartoon {
    @Id
    @Column(name = "number", columnDefinition = "INT")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "score")
    private double score;

    @Column(name = "episodes")
    private Integer episodes;

    @Column(name = "added")
    private Boolean added;

    public Cartoon(Integer id, String title, String description, double score, Integer episodes, Boolean added) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.score = score;
        this.episodes = episodes;
        this.added = added;
    }

    public Cartoon() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Integer getEpisodes() {
        return episodes;
    }

    public void setEpisodes(Integer episodes) {
        this.episodes = episodes;
    }

}
