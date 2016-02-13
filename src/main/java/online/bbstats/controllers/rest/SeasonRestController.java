package online.bbstats.controllers.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import online.bbstats.model.SeasonModel;
import online.bbstats.repository.domain.Season;
import online.bbstats.service.SeasonService;

@RestController
public class SeasonRestController {
    
    @Autowired
    private SeasonService seasonService;

    @RequestMapping(value = "/bbstats/v1/seasons", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SeasonModel> getSeasons() {
        List<Season> seasons = seasonService.findAllSeasons();
        List<SeasonModel> seasonModelList = new ArrayList<SeasonModel>();
        for (Season season: seasons) {
            SeasonModel seasonModel = new SeasonModel();
            BeanUtils.copyProperties(season, seasonModel);
            seasonModelList.add(seasonModel);
        }
        return seasonModelList;
    }
}
