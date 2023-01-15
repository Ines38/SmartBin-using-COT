package tn.supcom.cot;

import jakarta.nosql.mapping.Column;
import jakarta.nosql.mapping.Entity;
import jakarta.nosql.mapping.Id;

import javax.json.bind.annotation.JsonbVisibility;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonbVisibility(FieldPropertyVisibilityStrategy.class)
@Table(name = "Person")
public class Person {

    @Id
    private String name;

    @Column
    private Integer age;

    @Column
    private String telephones;

    public Person() {
    }

    public Person(String name, Integer age, String telephones) {
        this.name = name;
        this.age = age;
        this.telephones = telephones;
    }

  /*  public static PersonBuilder builder() {
        return new PersonBuilder();
    }*/

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", telephones=" + telephones +
                '}';
    }

  /*  public static class PersonBuilder {
        private String name;
        private int age;
        private String telephones;

        private PersonBuilder() {
        }

        public PersonBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PersonBuilder age(int age) {
            this.age = age;
            return this;
        }

        public PersonBuilder telephones(String telephones) {
            this.telephones = telephones;
            return this;
        }

        public Person build() {
            return new Person(name, age, telephones);
        }
    }*/
}
