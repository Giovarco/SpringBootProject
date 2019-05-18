package net.mignemi.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Design {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Design_Tag",
            joinColumns = {@JoinColumn(name = "design_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tag> tags;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] image;

    @Override
    public String toString() {
        return "Design{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tags=" + tags.stream()
                                .map(tag -> tag.getId().toString())
                                .collect(Collectors.joining(", ","[","]")) +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}