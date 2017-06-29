package ruazosa.hr.fer.officememo.Model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by shimu on 29.6.2017..
 */

public class FirebaseHandler {
    private static final String departmentRef="departments";
    private static final String userRef="users";
    private static final String postRef="posts";
    private static final String userDepartmentRef="usersdepartmentspair";


    public static String pushDepartment(Department department){
        DatabaseReference pushRef = FirebaseDatabase.getInstance().getReference(departmentRef).push();
        pushRef.setValue(department);
        return pushRef.getKey();
    }
    public static String pushUser(User user){
        DatabaseReference pushRef = FirebaseDatabase.getInstance().getReference(userRef).push();
        pushRef.setValue(user);
        return pushRef.getKey();
    }
    public static String pushPost(Post post){
        DatabaseReference pushRef = FirebaseDatabase.getInstance().getReference(postRef).push();
        pushRef.setValue(post);
        return pushRef.getKey();
    }
    public static String pushUserDepartmentPair(UserDepartmentPair userDepartmentPair){
        DatabaseReference pushRef = FirebaseDatabase.getInstance().getReference(userDepartmentRef).push();
        pushRef.setValue(userDepartmentPair);
        return pushRef.getKey();
    }
}
