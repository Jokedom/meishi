package com.zero.du.meishi.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zero.du.meishi.MainActivity;
import com.zero.du.meishi.R;
import com.zero.du.meishi.databinding.FragmentAboutBinding;
import com.zero.du.meishi.databinding.FragmentMineBinding;

import static android.content.Context.MODE_PRIVATE;


public class AboutFragment extends Fragment {

    private MineViewModel mineViewModel;
    private FragmentAboutBinding binding;
    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mineViewModel =
                new ViewModelProvider(this).get(MineViewModel.class);

        binding = FragmentAboutBinding.inflate(inflater, container, false);
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.GONE);

        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        BottomNavigationView viewById = getActivity().findViewById(R.id.nav_view);
        viewById.setVisibility(View.VISIBLE);

    }
}