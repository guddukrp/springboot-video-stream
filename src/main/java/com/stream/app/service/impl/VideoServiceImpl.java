package com.stream.app.service.impl;

import com.stream.app.entities.Video;
import com.stream.app.repositories.VideoRepository;
import com.stream.app.service.VideoService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class VideoServiceImpl implements VideoService
{
    @Value("${files.video}")
    String DIR;

    @PostConstruct
    public void init(){
        File file = new File(DIR);
        if(!file.exists()){
            file.mkdir();
            System.out.println("Folder Created");
        }else{
            System.out.println("Folder already created");
        }
    }

    private VideoRepository videoRepository;
    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }
    @Override
    public Video save(Video video, MultipartFile file)  {

        //original file name
        try{

        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        InputStream inputStream = file.getInputStream();

        //file path:create
        String cleanFileName = StringUtils.cleanPath(filename);

        //folder path: created
        String cleanFolder= StringUtils.cleanPath(DIR);

        //folder path with file name
        Path path = Paths.get(cleanFolder, cleanFileName);
        System.out.println(path);
        System.out.println(contentType);


        //copy file to the folder

            Files.copy(inputStream,path, StandardCopyOption.REPLACE_EXISTING);

        //video meta data
            video.setContentType(contentType);
            video.setFilePath(path.toString());
        //save metadata
            return videoRepository.save(video);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Video get(String videoId) {
        return null;
    }

    @Override
    public Video getByTitle(String title) {
        return null;
    }

    @Override
    public List<Video> getAllVideo() {
        return null;
    }
}
