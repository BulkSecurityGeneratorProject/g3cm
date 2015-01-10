package org.dyndns.tarotmc.g3cm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Character.
 */
@Entity
@Table(name = "T_CHARACTER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Character implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private String age;

    @Column(name = "description")
    private String description;

    @Column(name = "bio")
    private String bio;

    @OneToMany(mappedBy = "character")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CharacterAdvantage> characterAdvantages = new HashSet<>();

    @OneToMany(mappedBy = "character")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CharacterSkill> characterSkills = new HashSet<>();

    @OneToMany(mappedBy = "character")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CharacterAttribute> characterAttributes = new HashSet<>();

    @ManyToOne
    private User user;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Form> forms = new HashSet<>();

    @ManyToOne
    private Campaign campaign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<CharacterAdvantage> getCharacterAdvantages() {
        return characterAdvantages;
    }

    public void setCharacterAdvantages(Set<CharacterAdvantage> characterAdvantages) {
        this.characterAdvantages = characterAdvantages;
    }

    public Set<CharacterSkill> getCharacterSkills() {
        return characterSkills;
    }

    public void setCharacterSkills(Set<CharacterSkill> characterSkills) {
        this.characterSkills = characterSkills;
    }

    public Set<CharacterAttribute> getCharacterAttributes() {
        return characterAttributes;
    }

    public void setCharacterAttributes(Set<CharacterAttribute> characterAttributes) {
        this.characterAttributes = characterAttributes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Form> getForms() {
        return forms;
    }

    public void setForms(Set<Form> forms) {
        this.forms = forms;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Character character = (Character) o;

        if (id != null ? !id.equals(character.id) : character.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", age='" + age + "'" +
                ", description='" + description + "'" +
                ", bio='" + bio + "'" +
                '}';
    }
}
