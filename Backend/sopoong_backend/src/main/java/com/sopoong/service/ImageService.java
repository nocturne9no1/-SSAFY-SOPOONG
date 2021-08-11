package com.sopoong.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.sopoong.model.dto.ImageDto;
import com.sopoong.model.entity.Image;
import com.sopoong.repository.ImageRepository;

@Service
public class ImageService {
	private final ImageRepository imageRepository;
	
	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}
	
	@Transactional
	public void saveImage(ImageDto imageDto) throws Exception {
		Image image = Image.builder()
				.imageOriginTitle(imageDto.getImageOriginTitle())
				.imageTitle(imageDto.getImageTitle())
				.imagePath(imageDto.getImagePath())
				.build();
		
		imageRepository.save(image);
		return;
	}
	
	@Transactional
	public ImageDto getImage(Long id) {
		Image image = imageRepository.findById(id).get();
		
		ImageDto imageDto = ImageDto.builder()
							.imageIdx(id)
							.imageOriginTitle(image.getImageOriginTitle())
							.imageTitle(image.getImageTitle())
							.imagePath(image.getImagePath())
							.build();
		
		return imageDto;
	}
}
