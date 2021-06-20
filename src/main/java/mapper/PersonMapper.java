package mapper;

import domain.Person;
import dto.PersonDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    Person mapToPerson(PersonDto personDto);

    PersonDto mapToPersonDto(Person person);


}
