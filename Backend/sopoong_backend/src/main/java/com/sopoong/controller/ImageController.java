package com.sopoong.controller;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sopoong.common.BaseMessage;
import com.sopoong.service.ImageService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/auth")
public class ImageController {
	public static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	@Autowired
	private ImageService imageService;


/*	@PostMapping("/image/profile")
	@ApiOperation(value = "사진 등록")
	public ResponseEntity write(@RequestBody ProfileDto profile) throws IllegalStateException, NoSuchAlgorithmException, IOException {
		BaseMessage bm = imageService.saveProfile(image, "wpffl3333");
		return new ResponseEntity<BaseMessage>(new BaseMessage(bm.getHttpStatus(), bm.getData()), bm.getHeaders(), bm.getHttpStatus());
	} */
}
