package taskninja.android.models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "lists")
public class NinjaList {

	@DatabaseField(generatedId = true)
    private int id;
	
	@DatabaseField(unique = true)
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
    
    @Override
    public String toString(){
    	return getTitle();
    }
    
    public int getId() {
		return id;
	}
    
    
}
