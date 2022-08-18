package modules.task.models;

import modules.task.models.enums.Category;
import modules.task.models.enums.Priority;

import java.lang.reflect.Field;

public class Task {
    int uid;
    String name;
    String description;
    String priority;
    String category;

    String dueDate;

    String status;

    public Task(int uid, String name, String description, String priority, String category, String dueDate, String status){
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.category = category;
        this.dueDate = dueDate;
        this.status = status;

    }

    public int getUid(){
        return this.uid;
    }
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return this.priority;
    }

    public String getCategory() {
        return category;
    }

    public String getDueDate() {
        return dueDate;
    }
    public String getStatus() {
        return status;
    }


    public String toString() {
        StringBuilder result = new StringBuilder();
        String newLine = System.getProperty("line.separator");

        result.append( this.getClass().getName() );
        result.append( " Object {" );
        result.append(newLine);

        //determine fields declared in this class only (no fields of superclass)
        Field[] fields = this.getClass().getDeclaredFields();

        //print field names paired with their values
        for ( Field field : fields  ) {
            result.append("  ");
            try {
                result.append( field.getName() );
                result.append(": ");
                //requires access to private field:
                result.append( field.get(this) );
            } catch ( IllegalAccessException ex ) {
                System.out.println(ex);
            }
            result.append(newLine);
        }
        result.append("}");

        return result.toString();
    }

}
