# Anonymization-Website
## Running the Application

From the project root:

```bash
cd anonymizer
```

Then, depending on your OS:

**macOS / Linux:**
```bash
./mvnw spring-boot:run
```

**Windows:**
```bash
mvnw.cmd spring-boot:run
```

Once started, the site is available at: http://localhost:8080/home.html 
(or whatever port is configured — see below)

## Configuration

All configuration lives in `src/main/resources/application.yml`.

- **Server port**: set `server.port` (defaults to `8080` if not specified)
- Any other environment-specific settings should also be changed here

## Test Mode

By default, the application expects a running DUUI anonymization
component to actually process input.

To test the website **without** a live DUUI component:

1. Open `PipelineService.java`
2. Comment out line 94: `dUUIInteractions.addComponents(components);`
3. Uncomment line 96: `dUUIInteractions.getJcas().getView("textView").setDocumentText("This is a placeholder text for simulating the output!");`

This bypasses the real pipeline and returns a placeholder result instead,
so the site's request/response flow can be verified end-to-end without
a live component.

**To run against a real component instead:**

1. Start the `duui-anonymization` container
2. Enter its port in the "Component URL" field on the site
3. Make sure `PipelineService.java` has the real pipeline call active
   (line 94 uncommented, line 96 commented — i.e. the reverse of Test Mode above)

## Known Limitations / TODOs

- Currently supports a single user session at a time (pipeline state is
  shared, not per-session) — see TODO in `PipelineService.java`
- Only the TEXT modality is currently implemented