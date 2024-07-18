package com.movierecommender.main.jobs;


import com.google.inject.Inject;
import com.movierecommender.main.io.CassandraIo;
import com.movierecommender.main.model.RawRating;
import com.movierecommender.spark.RecommendationEngine;
import com.movierecommender.spark.als.TrainConfig;

public class TrainJob implements Job {
    private RecommendationEngine recommendationEngine;
    private CassandraIo<RawRating> ratingCassandraIo;

    @Inject
    public TrainJob(RecommendationEngine recommendationEngine, CassandraIo<RawRating> ratingCassandraIo) {
        this.recommendationEngine = recommendationEngine;
        this.ratingCassandraIo = ratingCassandraIo;
    }

    @Override
    public void execute() {
        TrainConfig trainConfig = new TrainConfig(10, 4);
        recommendationEngine.train(trainConfig, ratingCassandraIo);
    }

    @Override
    public String getName() {
        return "model.train";
    }
}
