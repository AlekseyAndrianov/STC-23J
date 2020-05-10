package part1.lesson02.task03;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Класс Person характеризуется полями age (возраст, целое число 0-100),
 * sex (пол – объект класса Sex со строковыми константами внутри MAN, WOMAN), name (имя - строка).
 */
@Getter
@Setter
@Builder
@ToString
public class Person {
    private int age;
    private Sex sex;
    private String name;

    public void setAge(int age) {
        if (age < 0 || age > 100)
            throw new IllegalArgumentException("Age must be from 0 to 100");
        this.age = age;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Person)) return false;
        final Person other = (Person) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getAge() != other.getAge()) return false;
        final Object this$sex = this.getSex();
        final Object other$sex = other.getSex();
        if (this$sex == null ? other$sex != null : !this$sex.equals(other$sex)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Person;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getAge();
        final Object $sex = this.getSex();
        result = result * PRIME + ($sex == null ? 43 : $sex.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public enum Sex {
        MAN,
        WOMAN
    }

}
