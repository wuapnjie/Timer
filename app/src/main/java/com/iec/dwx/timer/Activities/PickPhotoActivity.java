package com.iec.dwx.timer.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.iec.dwx.timer.Adapters.AlbumAdapter;
import com.iec.dwx.timer.Adapters.PhotoAdapter;
import com.iec.dwx.timer.Content.PhotoAlbumManager;
import com.iec.dwx.timer.R;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PickPhotoActivity extends AppCompatActivity {
    private static final String TAG = PickPhotoActivity.class.getSimpleName();
    public static final int STATE_PICK_ALBUM = 0;
    public static final int STATE_PICK_PHOTO = 1;

    private RecyclerView mAlbums;
    private RecyclerView mPhotos;
    private AlbumAdapter mAlbumAdapter;
    private PhotoAdapter mPhotoAdapter;

    private int mState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_photo);

        initial();

        Observable.defer(() -> PhotoAlbumManager.initial(this).getAlbumListObservable()).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(albumBeans -> {
            mAlbumAdapter.obtainData(albumBeans);

            mAlbumAdapter.setOnAlbumClickListener((v, position, args) -> pickPhoto(args));
        });
    }

    private void initial() {
        mAlbums = (RecyclerView) findViewById(R.id.rv_albums);
        mPhotos = (RecyclerView) findViewById(R.id.rv_photos);

        mAlbumAdapter = new AlbumAdapter(this);

        mAlbums.setAdapter(mAlbumAdapter);
        mAlbums.setLayoutManager(new LinearLayoutManager(this));

        ((Toolbar) findViewById(R.id.toolbar_pick_photo)).setNavigationOnClickListener(v -> onBackPressed());
    }

    private void pickPhoto(String bucketId) {
        mState = STATE_PICK_PHOTO;
        mAlbums.setVisibility(View.INVISIBLE);
        mPhotos.setVisibility(View.VISIBLE);
        mPhotos.setLayoutManager(new GridLayoutManager(this, 3));
        mPhotoAdapter = new PhotoAdapter(this);
        mPhotos.setAdapter(mPhotoAdapter);

        //TODO
        Observable.just(bucketId)
                .map(s -> PhotoAlbumManager.initial(this).getPhotosInAlbum(s))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(strings -> {
                    mPhotoAdapter.obtainData(strings);
                });
    }

    @Override
    public void onBackPressed() {
        if (mState == STATE_PICK_PHOTO) {
            mAlbums.setVisibility(View.VISIBLE);
            mPhotos.setVisibility(View.GONE);
        } else if (mState == STATE_PICK_ALBUM) {
            super.onBackPressed();
        }
    }
}
