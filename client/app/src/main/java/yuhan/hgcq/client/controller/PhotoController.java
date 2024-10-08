package yuhan.hgcq.client.controller;

import android.content.Context;
import android.net.Uri;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import yuhan.hgcq.client.config.NetworkClient;
import yuhan.hgcq.client.model.dto.photo.MovePhotoForm;
import yuhan.hgcq.client.model.dto.photo.PhotoDTO;
import yuhan.hgcq.client.model.service.PhotoService;

public class PhotoController {

    private PhotoService photoService;

    public PhotoController(Context context) {
        NetworkClient client = NetworkClient.getInstance(context.getApplicationContext());
        photoService = client.getPhotoService();
    }

    /**
     * 사진 업로드
     *
     * @param albumId   앨범 id
     * @param photoUris 사진 uri
     * @param creates   사진 날짜
     * @param callback  비동기 콜백
     */
    public void uploadPhoto(Long albumId, List<Uri> photoUris, List<LocalDateTime> creates, Callback<ResponseBody> callback) {
        RequestBody albumIdPart = RequestBody.create(
                MediaType.parse("text/plain"),
                String.valueOf(albumId)
        );

        List<MultipartBody.Part> fileParts = new ArrayList<>();
        for (Uri uri : photoUris) {
            File file = new File(String.valueOf(uri));
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
            fileParts.add(image);
        }

        List<RequestBody> createParts = new ArrayList<>();
        for (LocalDateTime createTime : creates) {
            RequestBody createPart = RequestBody.create(
                    MediaType.parse("text/plain"),
                    createTime.toString()
            );
            createParts.add(createPart);
        }

        Call<ResponseBody> call = photoService.uploadPhoto(albumIdPart, fileParts, createParts);
        call.enqueue(callback);
    }

    /**
     * 사진 삭제
     *
     * @param photoDTO 사진 DTO
     * @param callback 비동기 콜백
     */
    public void deletePhoto(PhotoDTO photoDTO, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = photoService.deletePhoto(photoDTO);
        call.enqueue(callback);
    }

    /**
     * 사진 삭제 취소
     *
     * @param photoDTO 사진 DTO
     * @param callback 비동기 콜백
     */
    public void cancelDeletePhoto(PhotoDTO photoDTO, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = photoService.cancelDeletePhoto(photoDTO);
        call.enqueue(callback);
    }

    /**
     * 앨범 이동
     *
     * @param movePhotoForm 앨범 이동 폼
     * @param callback      비동기 콜백
     */
    public void moveAlbumPhoto(MovePhotoForm movePhotoForm, Callback<ResponseBody> callback) {
        Call<ResponseBody> call = photoService.moveAlbumPhoto(movePhotoForm);
        call.enqueue(callback);
    }

    /**
     * 사진 자동 저장
     *
     * @param photoUris 사진 uri
     * @param teamId    팀 id
     * @param creates   사진 날짜
     * @param callback  비동기 콜백
     */
    public void autoSavePhoto(List<Uri> photoUris, Long teamId, List<LocalDateTime> creates, Callback<ResponseBody> callback) {
        RequestBody teamIdPart = RequestBody.create(
                MediaType.parse("text/plain"),
                String.valueOf(teamId)
        );

        List<MultipartBody.Part> fileParts = new ArrayList<>();
        for (Uri uri : photoUris) {
            File file = new File(String.valueOf(uri));
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part image = MultipartBody.Part.createFormData("image", file.getName(), reqFile);
            fileParts.add(image);
        }

        List<RequestBody> createParts = new ArrayList<>();
        for (LocalDateTime createTime : creates) {
            RequestBody createPart = RequestBody.create(
                    MediaType.parse("text/plain"),
                    createTime.toString()
            );
            createParts.add(createPart);
        }

        Call<ResponseBody> call = photoService.autoSavePhoto(fileParts, teamIdPart, createParts);
        call.enqueue(callback);
    }

    /**
     * 사진 리스트
     *
     * @param albumId  앨범 id
     * @param callback 비동기 콜백
     */
    public void photoList(Long albumId, Callback<List<PhotoDTO>> callback) {
        Call<List<PhotoDTO>> call = photoService.photoList(albumId);
        call.enqueue(callback);
    }

    /**
     * 사진 휴지통 리스트
     *
     * @param albumId  앨범 id
     * @param callback 비동기 콜백
     */
    public void photoTrashList(Long albumId, Callback<List<PhotoDTO>> callback) {
        Call<List<PhotoDTO>> call = photoService.photoTrashList(albumId);
        call.enqueue(callback);
    }
}
