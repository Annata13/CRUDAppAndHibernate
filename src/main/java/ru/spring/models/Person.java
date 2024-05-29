package ru.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;

    @NotEmpty(message = "Name should not be empty") //запрет пустого имени
    @Size(min=2, max=30, message = "Name should be between 2 and 30 characters") //диапазон символов в имени
    @Column(name = "name")
    private String name;

    @Min(value = 0,message = "Age should be greater than 0") // минимальное значение 0
   @Column(name = "age")
    private int age;

  @NotEmpty(message = "Email should not be empty") //запрет пустого имени
   @Email(message = "Email should be valid") //проверяет email
    @Column(name = "email")
    private String email;

    public Person( String name, int age, String email ) {
        this.name = name;
        this.age = age;
        this.email = email;

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NotEmpty(message = "Email should not be empty") @Email(message = "Email should be valid") String getEmail() {
        return email;
    }

    public void setEmail(@NotEmpty(message = "Email should not be empty") @Email(message = "Email should be valid") String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
