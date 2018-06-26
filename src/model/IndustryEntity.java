package model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Industry", schema = "deepView", catalog = "")
public class IndustryEntity {
    private int id;
    private String indCode;
    private String indName;

    @Id
    @Column(name = "ID", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "IndCode", nullable = true, length = 6)
    public String getIndCode() {
        return indCode;
    }

    public void setIndCode(String indCode) {
        this.indCode = indCode;
    }

    @Basic
    @Column(name = "IndName", nullable = true, length = 20)
    public String getIndName() {
        return indName;
    }

    public void setIndName(String indName) {
        this.indName = indName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndustryEntity that = (IndustryEntity) o;
        return id == that.id &&
                Objects.equals(indCode, that.indCode) &&
                Objects.equals(indName, that.indName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, indCode, indName);
    }
}
