package org.herovole.blogproj.presentation.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.herovole.blogproj.application.error.ApplicationProcessException;
import org.herovole.blogproj.application.error.UseCaseErrorType;
import org.herovole.blogproj.application.image.deleteimage.RemoveImage;
import org.herovole.blogproj.application.image.deleteimage.RemoveImageInput;
import org.herovole.blogproj.application.image.postimage.PostImage;
import org.herovole.blogproj.application.image.postimage.PostImageInput;
import org.herovole.blogproj.application.image.resourceprefix.GetResourcePrefix;
import org.herovole.blogproj.application.image.searchimages.SearchImages;
import org.herovole.blogproj.application.image.searchimages.SearchImagesInput;
import org.herovole.blogproj.domain.DomainInstanceGenerationException;
import org.herovole.blogproj.domain.FormContent;
import org.herovole.blogproj.domain.image.Image;
import org.herovole.blogproj.infra.filesystem.ImageAsMultipartFile;
import org.herovole.blogproj.presentation.AppServletRequest;
import org.herovole.blogproj.presentation.presenter.BasicPresenter;
import org.herovole.blogproj.presentation.presenter.GetResourcePrefixPresenter;
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

    private static final Logger logger = LoggerFactory.getLogger(AdminV1ImageController.class.getSimpleName());

    private final SearchImages searchImages;
    private final SearchImagesPresenter searchImagesPresenter;
    private final PostImage postImage;
    private final BasicPresenter postImagePresenter;
    private final RemoveImage removeImage;
    private final BasicPresenter removeImagePresenter;
    private final GetResourcePrefix getResourcePrefix;
    private final GetResourcePrefixPresenter getResourcePrefixPresenter;

    @Autowired
    public AdminV1ImageController(SearchImages searchImages, SearchImagesPresenter searchImagesPresenter,
                                  PostImage postImage, BasicPresenter postImagePresenter,
                                  RemoveImage removeImage, BasicPresenter removeImagePresenter,
                                  GetResourcePrefix getResourcePrefix, GetResourcePrefixPresenter getResourcePrefixPresenter) {
        this.searchImages = searchImages;
        this.searchImagesPresenter = searchImagesPresenter;
        this.postImage = postImage;
        this.postImagePresenter = postImagePresenter;
        this.removeImage = removeImage;
        this.removeImagePresenter = removeImagePresenter;
        this.getResourcePrefix = getResourcePrefix;
        this.getResourcePrefixPresenter = getResourcePrefixPresenter;
    }

    @GetMapping("/prefix")
    public ResponseEntity<String> getResourcePrefix() {

        try {
            getResourcePrefix.process();
        } catch (DomainInstanceGenerationException e) {
            logger.error("Error Bad Request : ", e);
            this.getResourcePrefixPresenter.setUseCaseErrorType(UseCaseErrorType.GENERIC_USER_ERROR);
        } catch (ApplicationProcessException e) {
            logger.error("Error Application Process Exception : ", e);
        } catch (Exception e) {
            logger.error("Error Internal Server Error : ", e);
            this.getResourcePrefixPresenter.setUseCaseErrorType(UseCaseErrorType.SERVER_ERROR);
        }
        return this.getResourcePrefixPresenter.buildResponseEntity();
    }

    @GetMapping
    public ResponseEntity<String> searchImages(
            HttpServletRequest httpServletRequest,
            @RequestParam Map<String, String> request) {
        logger.info("Endpoint : images (get) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().editsArticles()) {
            this.searchImagesPresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.searchImagesPresenter.buildResponseEntity();
        }
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
    public ResponseEntity<String> postImage(
            HttpServletRequest httpServletRequest,
            @RequestPart("image") MultipartFile file) {
        logger.info("Endpoint : images (post) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().editsImages()) {
            this.postImagePresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.postImagePresenter.buildResponseEntity();
        }
        Image image = ImageAsMultipartFile.of(file);

        try {
            PostImageInput input = PostImageInput.of(image);
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
            HttpServletRequest httpServletRequest,
            @RequestBody Map<String, String> request) {
        logger.info("Endpoint : images (delete) ");
        AppServletRequest servletRequest = AppServletRequest.of(httpServletRequest);
        if (!servletRequest.getAdminUserFromAttribute().getRole().editsImages()) {
            this.postImagePresenter.setUseCaseErrorType(UseCaseErrorType.AUTH_INSUFFICIENT);
            return this.postImagePresenter.buildResponseEntity();
        }

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
