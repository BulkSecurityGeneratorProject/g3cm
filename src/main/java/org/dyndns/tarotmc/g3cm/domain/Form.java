package org.dyndns.tarotmc.g3cm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Form.
 */
@Entity
@Table(name = "T_FORM")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Form implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "incon")
    private String incon;

    @Column(name = "physical_change")
    private String physicalChange;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Advantage> advantages = new HashSet<>();

    @OneToMany(mappedBy = "form")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormAttributeMod> formAttributeMods = new HashSet<>();

    @OneToMany(mappedBy = "form")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FormSkillMod> formSkillMods = new HashSet<>();

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

    public String getIncon() {
        return incon;
    }

    public void setIncon(String incon) {
        this.incon = incon;
    }

    public String getPhysicalChange() {
        return physicalChange;
    }

    public void setPhysicalChange(String physicalChange) {
        this.physicalChange = physicalChange;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Set<Advantage> getAdvantages() {
        return advantages;
    }

    public void setAdvantages(Set<Advantage> advantages) {
        this.advantages = advantages;
    }

    public Set<FormAttributeMod> getFormAttributeMods() {
        return formAttributeMods;
    }

    public void setFormAttributeMods(Set<FormAttributeMod> formAttributeMods) {
        this.formAttributeMods = formAttributeMods;
    }

    public Set<FormSkillMod> getFormSkillMods() {
        return formSkillMods;
    }

    public void setFormSkillMods(Set<FormSkillMod> formSkillMods) {
        this.formSkillMods = formSkillMods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Form form = (Form) o;

        if (id != null ? !id.equals(form.id) : form.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", incon='" + incon + "'" +
                ", physicalChange='" + physicalChange + "'" +
                ", sortOrder='" + sortOrder + "'" +
                '}';
    }
}
