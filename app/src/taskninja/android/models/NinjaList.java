package taskninja.android.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "lists")
public class NinjaList {

	@DatabaseField(id = true)
    private String title;
	
    
    public NinjaList() {
        // ORMLite needs a no-arg constructor 
    }
    
    public NinjaList(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }

}
