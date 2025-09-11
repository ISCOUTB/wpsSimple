package org.wpsim.AgroEcosystem.Messages;

import BESA.Kernel.Agent.Event.DataBESA;

/**
 *
 * @author jairo
 */
public class AgroEcosystemMessage extends DataBESA {
    private AgroEcosystemMessageType agroEcosystemMessageType;
    private String cropId;
    private String peasantAgentAlias;
    private String payload;
    private String date;

    /**
     * Constructor for AgroEcosystemMessage.
     *
     * @param agroEcosystemMessageType The type of agro ecosystem message
     * @param cropId The ID of the crop
     * @param date The date of the message
     * @param peasantAgentId The ID of the peasant agent
     */
    public AgroEcosystemMessage(AgroEcosystemMessageType agroEcosystemMessageType, String cropId, String date, String peasantAgentId) {
        this.agroEcosystemMessageType = agroEcosystemMessageType;
        this.cropId = cropId;
        this.date = date;
        this.peasantAgentAlias = peasantAgentId;
    }

    /**
     * Returns a string representation of the AgroEcosystemMessage.
     *
     * @return A string representation of the message
     */
    @Override
    public String toString() {
        return "---> WorldMessage{" + "worldMessageType=" + agroEcosystemMessageType + ", cropId=" + cropId + ", peasantAgentId=" + peasantAgentAlias + ", payload=" + payload + ", date=" + date + "}";
    }

    /**
     * Gets the peasant agent alias.
     *
     * @return The peasant agent alias
     */
    public String getPeasantAgentAlias() {
        return peasantAgentAlias;
    }

    /**
     * Sets the peasant agent alias.
     *
     * @param peasantAgentAlias The peasant agent alias to set
     */
    public void setPeasantAgentAlias(String peasantAgentAlias) {
        this.peasantAgentAlias = peasantAgentAlias;
    }

    /**
     * Gets the date of the message.
     *
     * @return The date of the message
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the date of the message.
     *
     * @param date The date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Gets the agro ecosystem message type.
     *
     * @return The agro ecosystem message type
     */
    public AgroEcosystemMessageType getWorldMessageType() {
        return agroEcosystemMessageType;
    }

    /**
     * Sets the agro ecosystem message type.
     *
     * @param agroEcosystemMessageType The message type to set
     */
    public void setWorldMessageType(AgroEcosystemMessageType agroEcosystemMessageType) {
        this.agroEcosystemMessageType = agroEcosystemMessageType;
    }

    /**
     * Gets the crop ID.
     *
     * @return The crop ID
     */
    public String getCropId() {
        return cropId;
    }

    /**
     * Sets the crop ID.
     *
     * @param cropId The crop ID to set
     */
    public void setCropId(String cropId) {
        this.cropId = cropId;
    }

    /**
     * Gets the payload of the message.
     *
     * @return The payload of the message
     */
    public String getPayload() {
        return payload;
    }

    /**
     * Sets the payload of the message.
     *
     * @param payload The payload to set
     */
    public void setPayload(String payload) {
        this.payload = payload;
    }
}
