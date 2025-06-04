/**
 * ==========================================================================
 * __      __ _ __   ___  *    WellProdSim                                  *
 * \ \ /\ / /| '_ \ / __| *    @version 1.0                                 *
 * \ V  V / | |_) |\__ \ *    @since 2023                                  *
 * \_/\_/  | .__/ |___/ *                                                 *
 * | |          *    @author Jairo Serrano                        *
 * |_|          *    @author Enrique Gonzalez                     *
 * ==========================================================================
 * Social Simulator used to estimate productivity and well-being of peasant *
 * families. It is event oriented, high concurrency, heterogeneous time     *
 * management and emotional reasoning BDI.                                  *
 * ==========================================================================
 */
package org.wpsim.WellProdSim.Config;

import BESA.Kernel.System.AdmBESA;
import BESA.Log.ReportBESA;
import com.google.gson.Gson;
import org.snakeyaml.engine.v2.api.Load;
import org.snakeyaml.engine.v2.api.LoadSettings;
import org.wpsim.PeasantFamily.Data.Utils.FarmingResource;
import org.wpsim.PeasantFamily.Data.PeasantFamilyProfile;
import org.wpsim.WellProdSim.wpsStart;

import java.io.*;
import java.util.*;

import static org.wpsim.WellProdSim.wpsStart.params;

/**
 * @author jairo
 */
public final class wpsConfig {

    private static final wpsConfig INSTANCE = new wpsConfig();
    private String SocietyAgentName;
    private String BankAgentName;
    private String MarketAgentName;
    private String ControlAgentName;
    private String PerturbationAgentName;
    private String GovernmentAgentName;
    private String ViewerAgentName;
    private String peasantType;
    private String rainfallConditions;
    private String perturbation;
    private String startSimulationDate;
    public int peasantSerialID;
    private Properties properties = new Properties();
    private PeasantFamilyProfile defaultPeasantFamilyProfile;
    private PeasantFamilyProfile highRiskFarmerProfile;
    private PeasantFamilyProfile thrivingFarmerProfile;
    private int smlv;

    /**
     *
     */
    private wpsConfig() {
        loadPeasantConfig();
        loadWPSConfig();
        this.peasantSerialID = 1;
        this.perturbation = "";

    }

    /**
     *
     */
    public static wpsConfig getInstance() {
        return INSTANCE;
    }

    public int getSmlv() {
        return smlv;
    }

    public void setSmlv(int smlv) {
        this.smlv = smlv;
    }

    public String getSocietyAgentName() {
        return SocietyAgentName;
    }

    public String getBankAgentName() {
        return BankAgentName;
    }

    public String getMarketAgentName() {
        return MarketAgentName;
    }

    public String getPerturbationAgentName() {
        return PerturbationAgentName;
    }

    public String getControlAgentName() {
        return ControlAgentName;
    }
    public void setControlAgentName(String name) {
        ControlAgentName = name;
    }

    public String getViewerAgentName() {
        return this.ViewerAgentName;
    }

    /**
     * @return
     */
    public PeasantFamilyProfile getDefaultPeasantFamilyProfile() {
        return defaultPeasantFamilyProfile.clone();
    }

    /**
     * @return
     */
    public String getStartSimulationDate() {
        return startSimulationDate;
    }

    /**
     * @return
     */
    public String getPerturbation() {
        return perturbation;
    }

    /**
     * @param perturbation
     */
    public void setPerturbation(String perturbation) {
        this.perturbation = perturbation;
    }

    public Map<String, FarmingResource> loadMarketConfig() {
        Map<String, FarmingResource> priceList = new HashMap<>();

        try {
            String[] resourceNames = {
                    "water", "seeds", "pesticides",
                    "tools", "livestock", "rice", "roots"
            };

            for (String resourceName : resourceNames) {
                priceList.put(resourceName,
                        new FarmingResource(
                                resourceName,
                                properties.getProperty(
                                        "market." + resourceName + ".price"
                                ),
                                properties.getProperty(
                                        "market." + resourceName + ".quantity"
                                )
                        )
                );
            }
            return priceList;
        } catch (Exception e) {
            ReportBESA.error(e.getMessage());
        }
        return null;
    }

    private void loadWPSConfig() {

        int currentYear = java.time.LocalDate.now().getYear();
        String start_date = "01/01/" + currentYear;

        // @TODO: Incluir todas las config del wpsStart
        try {
            properties.load(loadFileAsStream("wpsConfig.properties"));
            this.startSimulationDate = start_date;
            this.BankAgentName = properties.getProperty("bank.name");
            this.ControlAgentName = properties.getProperty("control.name");
            this.MarketAgentName = properties.getProperty("market.name");
            this.SocietyAgentName = properties.getProperty("society.name");
            this.PerturbationAgentName = properties.getProperty("perturbation.name");
            this.ViewerAgentName = properties.getProperty("viewer.name");
            this.GovernmentAgentName = properties.getProperty("government.name");
            this.smlv = Integer.parseInt(properties.getProperty("government.smlv"));
        } catch (IOException e) {
            System.out.println(e.getMessage() + " no encontré loadWPSConfig");
        }
    }

    private void loadPeasantConfig() {
        LoadSettings settings = LoadSettings.builder().build();
        Load load = new Load(settings);
        Map<String, Object> data;

        String jsonData;
        String yamlContent;
        Gson gson = new Gson();

        yamlContent = loadFile("PeasantFamilyProfile.yml");
        data = (Map<String, Object>) load.loadFromString(yamlContent);
        Map<String, Object> regularPeasant = (Map<String, Object>) data.get("StablePeasant");
        jsonData = gson.toJson(regularPeasant);
        defaultPeasantFamilyProfile = gson.fromJson(jsonData, PeasantFamilyProfile.class);
    }

    public List<String> getFuzzyRulesList(String mode) {
        String rawFile = null;
        rawFile = loadFile("data.fuzzyRules/" + mode + ".txt");
        assert rawFile != null;
        return Arrays.asList(rawFile.split("\n"));
    }

    private InputStream loadFileAsStream(String fileName) throws FileNotFoundException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            File file = new File(fileName);
            if (file.exists()) {
                inputStream = new FileInputStream(file);
            } else {
                throw new FileNotFoundException("No se pudo encontrar " + fileName + " ni dentro del JAR ni en el sistema de archivos");
            }
        }
        return inputStream;
    }


    public String loadFile(String fileName) {
        InputStream inputStream = null;
        try {
            // Intentar cargar desde dentro del JAR
            inputStream = getClass().getClassLoader().getResourceAsStream(fileName);

            // Si no se encuentra dentro del JAR, intentar cargarlo desde el sistema de archivos
            if (inputStream == null) {
                File file = new File(fileName);
                if (file.exists()) {
                    inputStream = new FileInputStream(file);
                } else {
                    throw new FileNotFoundException("No se pudo encontrar " + fileName + " ni dentro del JAR ni en el sistema de archivos");
                }
            }

            // Convertir InputStream a String
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            return stringBuilder.toString();

        } catch (IOException e) {
            System.out.println(e.getMessage() + " no encontré loadFile");
            ReportBESA.error(e);
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage() + " no encontré loadFile close");
                    ReportBESA.error(e.getMessage());
                }
            }
        }
    }


    private double generateRandomNumber(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

    public PeasantFamilyProfile getFarmerProfile() {

        PeasantFamilyProfile pfProfile = this.getDefaultPeasantFamilyProfile();

        double rnd = 1 + generateRandomNumber(
                getDoubleProperty("pfagent.variance") * -1,
                getDoubleProperty("pfagent.variance")
        );

        if (params.money != -1) {
            pfProfile.setInitialMoney((int) (params.money * rnd));
            pfProfile.setMoney((int) (params.money * rnd));
        } else {
            pfProfile.setInitialMoney((int) (pfProfile.getMoney() * rnd));
            pfProfile.setMoney((int) (pfProfile.getMoney() * rnd));
        }

        if (params.water != -1) {
            pfProfile.setWaterAvailable((int) (params.water * rnd));
        } else {
            pfProfile.setWaterAvailable((int) (pfProfile.getWaterAvailable() * rnd));
        }

        if (params.seeds != -1) {
            pfProfile.setSeeds((int) (params.seeds * rnd));
        } else {
            pfProfile.setSeeds((int) (pfProfile.getSeeds() * rnd));
        }

        if (params.tools != -1) {
            pfProfile.setTools((int) (params.tools * rnd));
        }

        pfProfile.setHealth((int) (pfProfile.getHealth() * rnd));
        pfProfile.setInitialHealth((int) (pfProfile.getHealth() * rnd));
        pfProfile.setCropSize((int) (pfProfile.getCropSize() * rnd));
        pfProfile.setPeasantFamilyAffinity(pfProfile.getPeasantFamilyAffinity() * rnd);
        pfProfile.setPeasantFriendsAffinity(pfProfile.getPeasantFriendsAffinity() * rnd);
        pfProfile.setPeasantLeisureAffinity(pfProfile.getPeasantLeisureAffinity() * rnd);
        pfProfile.setSocialAffinity(pfProfile.getSocialAffinity() * rnd);
        pfProfile.setMinimumVital(wpsStart.config.getIntProperty("pfagent.minimalVital") * rnd);

        Random rand = new Random();
        if (rand.nextInt(101) <= getIntProperty("society.criminality")) {
            pfProfile.setCriminalityAffinity(true);
        }

        return pfProfile;
    }

    public synchronized String getUniqueFarmerName() {
        return AdmBESA.getInstance().getConfigBESA().getAliasContainer() + "PeasantFamily" + peasantSerialID++;
    }

    public String getGovernmentAgentName() {
        return this.GovernmentAgentName;
    }

    public String getStringProperty(String property) {
        return properties.getProperty(property, "");
    }

    public Double getDoubleProperty(String property) {
        try {
            return Double.parseDouble(properties.getProperty(property));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public int getIntProperty(String property) {
        try {
            return Integer.parseInt(properties.getProperty(property));
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean getBooleanProperty(String property) {
        return Boolean.parseBoolean(properties.getProperty(property, ""));
    }

    public long getLongProperty(String property) {
        return Long.parseLong(properties.getProperty(property, "0.0"));
    }

    public void setViewerAgentName(String name) {
        this.ViewerAgentName = name;
    }
}
