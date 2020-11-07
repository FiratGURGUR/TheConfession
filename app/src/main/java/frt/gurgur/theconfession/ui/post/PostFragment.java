package frt.gurgur.theconfession.ui.post;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.data.remote.APIResponseModel;
import frt.gurgur.theconfession.databinding.FragmentPostBinding;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.main.MainFragment;
import frt.gurgur.theconfession.ui.user.register.RegisterViewModel;
import frt.gurgur.theconfession.util.PreferencesHelper;

import static android.app.Activity.RESULT_OK;


public class PostFragment extends BaseFragment implements View.OnClickListener {
    FragmentPostBinding binding;
    public static final String FRAGMENT_TAG = "PostFragment";

    @Inject
    ViewModelFactory vmFactory;
    PostViewModel vm;
    @Inject
    PreferencesHelper preferencesHelper;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.buttonSharePost)
    Button buttonSharePost;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.ivSelected)
    ImageView ivSelected;
    @BindView(R.id.ivChooseImage)
    ImageView ivChooseImage;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false);
        View view = binding.getRoot();
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        vm = ViewModelProviders.of(this, vmFactory).get(PostViewModel.class);

        observeCreatePost();
        this.observeLoadStatus();
        this.observerErrorStatus();
    }

    public void initView() {
        buttonSharePost.setOnClickListener(this);
        ivChooseImage.setOnClickListener(this);
        binding.setUser(preferencesHelper.getUser());
    }

    public void observeCreatePost() {
        vm.getResponse().observe(this, new Observer<APIResponseModel>() {
            @Override
            public void onChanged(APIResponseModel apiResponseModel) {
                //shared preferences kaydetme yapma
                onError(getContext(), apiResponseModel.getMessage());
            }
        });
    }

    @Override
    protected void observerErrorStatus() {
        vm.getErrorStatus().observe(this,
                error -> {
                    if (error != null) {
                        onError(getContext(), error);
                        showProgressBar(false);
                        Log.e("fff", "Error");
                    }
                });
    }

    @Override
    protected void observeLoadStatus() {
        vm.getLoadingStatus().observe(
                this,
                isLoading -> showProgressBar(isLoading)

        );
    }

    private void showProgressBar(boolean isVisible) {
        if (isVisible) {
            progressBar.setVisibility(View.VISIBLE);
            Log.e("fff", "loading T");
        } else {
            progressBar.setVisibility(View.GONE);
            Log.e("fff", "loading F");
        }
    }

    public void startChoose() {
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2000);
        } else {
            openGallery();
        }
    }

    public void openGallery() {
       /* Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }*/
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Uri returnUri = data.getData();

                try {
                    Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                    UUID uuid = UUID.randomUUID();
                    saveee(bitmapImage, uuid.toString());
                    ivSelected.setImageBitmap(bitmapImage);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(mainActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSharePost:
                PostRequestModel model = new PostRequestModel(etContent.getText().toString().trim(), preferencesHelper.getUserId());
                vm.createPost(model);
                break;
            case R.id.ivChooseImage:
                startChoose();
                break;
        }
    }


    File file;

    public void saveee(Bitmap bmp, String name) throws IOException {
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/firat";
        File dir = new File(file_path);

        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, name + ".png");
        FileOutputStream fOut = new FileOutputStream(file);

        bmp.compress(Bitmap.CompressFormat.PNG, 85, fOut);
        fOut.flush();
        fOut.close();
        this.file = file;
    }





}