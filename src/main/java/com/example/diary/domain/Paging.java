package com.example.diary.domain;

import java.util.ArrayList;
import java.util.List;


import com.example.diary.dto.MiniDiaryDto;

public class Paging {

    private  List<MiniDiaryDto>  p1;
    private  List<MiniDiaryDto>  p2;
    private  List<MiniDiaryDto>  p3;
    private  List<MiniDiaryDto>  p4;
    private  List<MiniDiaryDto>  p5;
  
    
    public Paging(List<MiniDiaryDto> list) {
        int n =0;
        
        if(list.size() % 6 == 0) {
            n = list.size() / 6;
        } else {
            n = list.size() / 6 +1;
        }
        
        for (int i = 0; i < list.size(); i++) {
            
        }
        
    }
    
}
