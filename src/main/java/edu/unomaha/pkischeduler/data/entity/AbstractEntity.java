package edu.unomaha.pkischeduler.data.entity;

import javax.persistence.*;

/**
 * Abstract mapped superclass which provides a base for all entities.
 */
@MappedSuperclass
public abstract class AbstractEntity {

    /**
     * The object ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "idgenerator")
    // The initial value is to account for data.sql demo data ids
    @SequenceGenerator(name = "idgenerator", initialValue = 1000)
    private Long id;

    /**
     * Provides the object ID
     * @return The object ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifies the object ID
     * @param id The object ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Provides a hash code value for the object.
     * @return A hash code value for the object.
     */
    @Override
    public int hashCode() {
        if (getId() != null) {
            return getId().hashCode();
        }
        return super.hashCode();
    }

    /**
     * This method determines equality of database objects
     * @param obj the object being compared
     * @return A boolean value indicating object equality
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false; // null or other class
        }
        AbstractEntity other = (AbstractEntity) obj;

        if (getId() != null) {
            return getId().equals(other.getId());
        }
        return super.equals(other);
    }
}
