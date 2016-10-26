package gigafortress.androidresidemenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * User: special
 * Date: 13-12-22
 * Time: 下午3:28
 * Mail: specialcyci@gmail.com
 */
public class SettingsFragment extends Fragment {

    Button btn1, btn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        Log.e(TAG, "onCreateView: 111");
        v = inflater.inflate(R.layout.settings, container, false);
        btn1 = (Button) v.findViewById(R.id.testButton1);
        btn2 = (Button) v.findViewById(R.id.testButton2);
        btn1.setOnClickListener(l);
        btn2.setOnClickListener(l);
        return v;
    }

    private String TAG = "settingsFragment";
    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.testButton1:
                    Log.e(TAG, "onClick: 1" );
                    changeFragment(new TestFragment1());
                    break;
                case R.id.testButton2:
                    Log.e(TAG, "onClick: 2" );
                    changeFragment(new TestFragment2());
                    break;
            }
        }
    };

    private void changeFragment(Fragment targetFragment) {
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.testFragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
