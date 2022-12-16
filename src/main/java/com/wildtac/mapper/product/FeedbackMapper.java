package com.wildtac.mapper.product;

import com.wildtac.domain.product.feedback.Feedback;
import com.wildtac.dto.product.feedback.FeedbackAbstractDto;
import com.wildtac.dto.product.feedback.FeedbackDto;
import com.wildtac.mapper.StructMapper;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class FeedbackMapper implements StructMapper<FeedbackAbstractDto, Feedback> {

    private final ModelMapper modelMapper;

    @Override
    public List<Feedback> fromDtoListToObjectList(List<FeedbackAbstractDto> feedbackDtoList) {
        return modelMapper.map(feedbackDtoList, new TypeToken<List<Feedback>>() {}.getType());
    }

    @Override
    public List<FeedbackAbstractDto> fromObjectListToDtoList(List<Feedback> feedbackList) {
        return modelMapper.map(feedbackList, new TypeToken<List<FeedbackDto>>() {}.getType());
    }

    @Override
    public Feedback fromDtoToObject(FeedbackAbstractDto feedbackDto) {
        return modelMapper.map(feedbackDto, Feedback.class);
    }

    @Override
    public FeedbackDto fromObjectToDto(Feedback feedback) {
        return modelMapper.map(feedback, FeedbackDto.class);
    }
}
