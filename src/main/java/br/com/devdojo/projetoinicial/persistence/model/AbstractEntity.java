package br.com.devdojo.projetoinicial.persistence.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Andre Ribeiro on 3/22/2017.
 */
public class AbstractEntity implements Serializable {
    @JsonProperty("id")
    protected Integer id;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AbstractEntity) {
            if (obj.getClass() == this.getClass()) {
                if (getId() != null && ((AbstractEntity) obj).getId().equals(getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + "id=" + id + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
