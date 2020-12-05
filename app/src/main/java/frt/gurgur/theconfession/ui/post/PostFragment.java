package frt.gurgur.theconfession.ui.post;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.AndroidSupportInjection;
import frt.gurgur.theconfession.R;
import frt.gurgur.theconfession.model.APIResponseModel;
import frt.gurgur.theconfession.databinding.FragmentPostBinding;
import frt.gurgur.theconfession.model.post.PostRequestModel;
import frt.gurgur.theconfession.ui.ViewModelFactory;
import frt.gurgur.theconfession.ui.base.BaseFragment;
import frt.gurgur.theconfession.ui.post.PostViewModel;
import frt.gurgur.theconfession.ui.post.giphy.GiphyListFragment;
import frt.gurgur.theconfession.util.Common;
import frt.gurgur.theconfession.util.Constants;
import frt.gurgur.theconfession.util.Helper;
import frt.gurgur.theconfession.util.PreferencesHelper;
import frt.gurgur.theconfession.util.Utils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


import static android.app.Activity.RESULT_OK;


public class PostFragment extends BaseFragment implements View.OnClickListener {
    FragmentPostBinding binding;
    File file;
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
    @BindView(R.id.ivChooseGif)
    ImageView ivChooseGif;
    @BindView(R.id.btnImageCancel)
    ImageView btnImageCancel;
    @BindView(R.id.imageLayout)
    ConstraintLayout imageLayout;

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
        showBackButton(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showBackButton(false);
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
        observeCreatePostWithImage();
        this.observeLoadStatus();
        this.observerErrorStatus();
    }

    public void initView() {
        buttonSharePost.setOnClickListener(this);
        ivChooseImage.setOnClickListener(this);
        ivChooseGif.setOnClickListener(this);
        btnImageCancel.setOnClickListener(this);
        imageLayout.setVisibility(View.GONE);
        binding.setUser(preferencesHelper.getUser());
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length()==0){
                    buttonSharePost.setEnabled(false);
                    buttonSharePost.setAlpha(0.40f);
                }else {
                    buttonSharePost.setEnabled(true);
                    buttonSharePost.setAlpha(1);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Helper.keyboardVisibility(getContext(), getView(), new Constants.KeyboardVisibility() {
            @Override
            public void onKeyboardOpen() {
                showNavigation(false);
            }

            @Override
            public void onKeyboardClose() {
                showNavigation(true);
            }
        });


    }

    public void observeCreatePost() {
        vm.getResponse().observe(this, new Observer<APIResponseModel>() {
            @Override
            public void onChanged(APIResponseModel apiResponseModel) {
                onError(getContext(), apiResponseModel.getMessage());
                afterSharePost();

            }
        });
    }

    public void observeCreatePostWithImage() {
        vm.getResponseWithImage().observe(this, new Observer<APIResponseModel>() {
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
                        onError(getContext(), error.getMessage());
                        showProgressBar(false);
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
        } else {
            progressBar.setVisibility(View.GONE);
        }
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
                    file = Utils.saveee(bitmapImage, uuid.toString());
                    imageLayout.setVisibility(View.VISIBLE);
                    ivSelected.setImageBitmap(bitmapImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonSharePost:

                if (isShareable()){
                    if (file == null) {
                        shareNormlaPost();
                    } else {
                        sharePostWithImage();
                    }
                }else {
                    //uyarı göster
                    Common.customAlert(getContext() ,getString(R.string.share_post_validation_error), getString(R.string.action_gotit),false);
                }
                break;
            case R.id.ivChooseImage:
                startChoose();
                break;
            case R.id.ivChooseGif:
                multipleStackNavigator.start(new GiphyListFragment());
                break;
            case R.id.btnImageCancel:
                file = null;
                imageLayout.setVisibility(View.GONE);
                break;
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
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    public void shareNormlaPost() {
        PostRequestModel model = new PostRequestModel(etContent.getText().toString().trim(), preferencesHelper.getUserId());
        vm.createPost(model);
    }

    public void sharePostWithImage(){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("content_image", file.getName(), requestBody);
        RequestBody content = RequestBody.create(MediaType.parse("text/plain"), etContent.getText().toString().trim());
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(preferencesHelper.getUserId()));
        vm.createPostWithImage(fileToUpload, userId, content);
    }


    public void afterSharePost(){
        file=null;
        imageLayout.setVisibility(View.GONE);
        etContent.setText("");
        multipleStackNavigator.goBack();
    }


    public boolean isShareable(){
        boolean summary=false;
        if (etContent.getText().toString().trim().length()>0){
            summary = true;
        }else {
            if (file == null){
                summary = false;
            }else {
                summary = true;
            }
        }
        return summary;
    }

}