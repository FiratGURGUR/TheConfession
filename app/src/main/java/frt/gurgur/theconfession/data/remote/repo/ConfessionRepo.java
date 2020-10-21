package frt.gurgur.theconfession.data.remote.repo;

import javax.inject.Inject;

import frt.gurgur.theconfession.data.remote.APIService;

public class ConfessionRepo {
    private final APIService api;

    @Inject
    public ConfessionRepo(APIService api) {
        this.api = api;
    }
}
