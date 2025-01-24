package org.herovole.blogproj.presentation.controller;

import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.image.deleteimage.RemoveImage;
import org.herovole.blogproj.application.image.deleteimage.RemoveImageInput;
import org.herovole.blogproj.application.image.postimage.PostImage;
import org.herovole.blogproj.application.image.postimage.PostImageInput;
import org.herovole.blogproj.application.image.searchimages.SearchImages;
import org.herovole.blogproj.application.image.searchimages.SearchImagesInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.infra.filesystem.ImageAsMultipartFile;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.herovole.blogproj.presentation.presenter.SearchImagesPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/images")
public class AdminV1ImageController {

    Logger logger = LoggerFactory.getLogger(AdminV1ImageController.class.getSimpleName());

    private final SearchImages searchImages;
    private final SearchImagesPresenter searchImagesPresenter;
    private final PostImage postImage;
    private final BasicPresenter postImagePresenter;
    private final RemoveImage removeImage;
    private final BasicPresenter removeImagePresenter;

    @Autowired
    public AdminV1ImageController(SearchImages searchImages, SearchImagesPresenter searchImagesPresenter, PostImage postImage, BasicPresenter postImagePresenter, RemoveImage removeImage, BasicPresenter removeImagePresenter) {
        this.searchImages = searchImages;
        this.searchImagesPresenter = searchImagesPresenter;
        this.postImage = postImage;
        this.postImagePresenter = postImagePresenter;
        this.removeImage = removeImage;
        this.removeImagePresenter = removeImagePresenter;
    }

    @GetMapping
    public ResponseEntity<String> searchImages(
            @RequestParam Map<String, String> request) {

        FormContent formContent = FormContent.of(request);
        SearchImagesInput input = SearchImagesInput.of(formContent);
        try {
            searchImages.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.searchImagesPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.searchImagesPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.searchImagesPresenter.buildResponseEntity();
    }

    @PostMapping
    public ResponseEntity<String> postImage(@RequestPart("image") MultipartFile file) {
        System.out.println("endpoint : post");
        System.out.println("/api/v1/images");
        if (!file.isEmpty()) {
            System.out.println("Received file: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize() + " bytes");
        }

        Image image = ImageAsMultipartFile.of(file);
        PostImageInput input = PostImageInput.of(image);

        try {
            postImage.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.postImagePresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.postImagePresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.postImagePresenter.buildResponseEntity();
    }

    @DeleteMapping
    public ResponseEntity<String> removeImage(
            @RequestBody Map<String, String> request) {

        System.out.println("endpoint : delete");
        System.out.println("/api/v1/images");
        System.out.println(request);

        FormContent formContent = FormContent.of(request);
        RemoveImageInput input = RemoveImageInput.of(formContent);
        try {
            removeImage.process(input);
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.removeImagePresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.removeImagePresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.removeImagePresenter.buildResponseEntity();
    }
}
