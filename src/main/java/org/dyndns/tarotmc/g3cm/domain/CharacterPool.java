package org.dyndns.tarotmc.g3cm.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A CharacterPool.
 */
@Entity
@Table(name = "T_CHARACTERPOOL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CharacterPool implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "max")
    private Long max;

    @Column(name = "current")
    private Long current;

    @ManyToOne
    private Character character;

    @ManyToOne
    private Pool pool;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public Pool getPool() {
        return pool;
    }

    public void setPool(Pool pool) {
        this.pool = pool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CharacterPool characterPool = (CharacterPool) o;

        if (id != null ? !id.equals(characterPool.id) : characterPool.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return "CharacterPool{" +
                "id=" + id +
                ", max='" + max + "'" +
                ", current='" + current + "'" +
                '}';
    }
}
