package net.mignemi.portfolio.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(mappedBy = "tags")
    private List<Design> designs;

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", designs=" + designs.stream()
                .map(design -> design.getId().toString())
                .collect(Collectors.joining(", ", "[", "]")) +
                '}';
    }
}