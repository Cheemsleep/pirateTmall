package tmall.bean;

import java.util.List;

/**
 * 1. 基本属性的getter、setter
 * 2. 与Category的多对一关系  一个Property对应一个Category，一个Category对应多个Property
 */
public class Property {
    private int id;
    private String name;
    Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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

}
