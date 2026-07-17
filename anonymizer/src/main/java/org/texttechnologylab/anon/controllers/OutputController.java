package org.texttechnologylab.anon.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.texttechnologylab.anon.config.InputProperties;

@RestController
@RequestMapping
public class OutputController {
    @Autowired
    private InputProperties inputProperties;
}
