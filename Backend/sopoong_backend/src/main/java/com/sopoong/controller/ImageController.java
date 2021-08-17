package com.sopoong.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sopoong.common.BaseMessage;
import com.sopoong.model.dto.ImageDto;
import com.sopoong.service.ImageService;
import com.sopoong.util.MD5Generator;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class ImageController {
	public static final Logger logger = LoggerFactory.getLogger(ImageController.class);

	private ImageService imageService;

	public ImageController(ImageService imageService) {
		this.imageService = imageService;
	}

	@PostMapping("/image/upload")
	@ApiOperation(value = "사진 등록")
	public ResponseEntity write(@RequestBody List<MultipartFile> images) {
		try {

			// 실행되는 위치의 files 폴더에 파일이 저장됨
			String savePath = System.getProperty("user.dir") + "\\image";

			// 파일리스트가 비어있는 경우
			if (images.isEmpty()) {
				return new ResponseEntity("[fail]: List Empty", HttpStatus.NO_CONTENT);
			}

			// 파일이 저장되는 폴더가 없으면 폴더 생성
			if (!new File(savePath).exists()) {
				new File(savePath).mkdir();
			}

			// 다중 파일 업로드 처리
			for (MultipartFile image : images) {

				if (images.isEmpty()) // 파일이 빈 파일인 경우
					return new ResponseEntity("[fail]: File Empty", HttpStatus.NO_CONTENT);

				String contentType = image.getContentType();
				
				if(contentType == null) {
					return new ResponseEntity("[fail]: 확장자 없음", HttpStatus.BAD_REQUEST);
				}
					
				String imageOriginTitle = System.nanoTime() + image.getOriginalFilename(); // 파일명 중복을 피하기 위해 나노초까지 받아옴
				String imageTitle = new MD5Generator(imageOriginTitle).toString(); // 서버에 저장 값은 MD5의 체크섬 값

				String imagePath = savePath + "\\" + imageOriginTitle;
				image.transferTo(new File(imagePath));

				ImageDto imageDto = new ImageDto();
				imageDto.setImageOriginTitle(imageOriginTitle);
				imageDto.setImageTitle(imageTitle);
				imageDto.setImagePath(imagePath);

				imageService.saveImage(imageDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity("success", HttpStatus.OK);
	}
}
