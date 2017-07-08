package ruazosa.hr.fer.officememo.Controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import durdinapps.rxfirebase2.DataSnapshotMapper;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import ruazosa.hr.fer.officememo.Model.OfficeMemo;
import ruazosa.hr.fer.officememo.Model.User;
import ruazosa.hr.fer.officememo.R;

/**
 * Created by shimun on 08.07.17..
 */

class FeedViewHolder extends RecyclerView.ViewHolder{
    Context context;
    TextView name;
    CompoundButton toggle;
    FeedAdapter  adapter;
    ImageView profile;
    View deli;
    public FeedViewHolder(Context context,View itemView, FeedAdapter adapter) {
        super(itemView);
        this.context = context;
        this.adapter = adapter;
        name = (TextView) itemView.findViewById(R.id.textViewSubscription);
        toggle = (CompoundButton) itemView.findViewById(R.id.switchSubscription);
        profile = (ImageView) itemView.findViewById(R.id.imageViewSubscription);
        deli = itemView.findViewById(R.id.viewSubscription);
        RxCompoundButton.checkedChanges(toggle).subscribe(aBoolean -> {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users").child(OfficeMemo.getUserUid());
            RxFirebaseDatabase.observeSingleValueEvent(ref, DataSnapshotMapper.of(User.class))
                    .subscribe(user -> {
                        String did = adapter.getList().get(getAdapterPosition()).getDid();
                        if(aBoolean){
                            user.getSubscriptions().add(did);
                            FirebaseMessaging.getInstance().subscribeToTopic(did);
                        }
                        else{
                            user.getSubscriptions().remove(did);
                            FirebaseMessaging.getInstance().unsubscribeFromTopic(did);
                        }
                        ref.setValue(user);
                    });
        });

    }
}
