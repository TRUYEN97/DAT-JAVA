/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nextone.controller.settingElement.imp.yardSetting;

import com.nextone.common.YardConfig;
import com.nextone.controller.settingElement.IElementSetting;
import com.nextone.model.yardConfigMode.ContestConfig;
import com.nextone.view.frame.EditElementShowRoom;
import com.nextone.view.frame.MenuShowRoom;
import java.util.List;

/**
 *
 * @author Admin
 */
public class YardContestConfigSetting implements IElementSetting {

    private final String name;
    private final YardConfig yardConfig;
    private ContestConfig contestConfig;
    private List<ContestConfig> contestConfigs;
    private EditElementShowRoom<YardContestConfigSetting> linesShowRoom;
    private MenuShowRoom<LineConfigSetting> showRoom;

    public YardContestConfigSetting(String name, List<ContestConfig> contestConfigs) {
        this.name = name;
        this.contestConfigs = contestConfigs;
        this.yardConfig = YardConfig.getInstance();
    }

    public YardContestConfigSetting(String name, ContestConfig contestConfig) {
        this.name = name;
        this.contestConfig = contestConfig;
        this.yardConfig = YardConfig.getInstance();
    }

    @Override
    public void close() {
        if (this.showRoom != null) {
            this.showRoom.close();
        }
        if (this.linesShowRoom != null) {
            this.linesShowRoom.close();
        }
    }

    @Override
    public void run() {
        if (this.contestConfig != null) {
            this.showRoom = new MenuShowRoom<>(LineConfigSetting.class,
                    (t) -> {
                        if (t == null) {
                            return false;
                        }
                        new Thread(t).start();
                        return true;
                    },
                    (t) -> {
                        if (t == null) {
                            return false;
                        }
                        t.close();
                        return true;
                    }, 2, 3);
            this.showRoom.setShowRoomName(getName());
            this.showRoom.removeAllElement();
            this.showRoom.addElement(new LineConfigSetting<>("Vạch kẻ",
                    contestConfig.getDistanceLine(), (v) -> {
                if (v == null) {
                    return false;
                }
                contestConfig.setDistanceLine(v);
                yardConfig.update();
                return true;
            }));
            this.showRoom.addElement(new LineConfigSetting<>("Thoát bài",
                    contestConfig.getDistanceOut(), (v) -> {
                if (v == null) {
                    return false;
                }
                contestConfig.setDistanceOut(v);
                yardConfig.update();
                return true;
            }));
            this.showRoom.addElement(new LineConfigSetting<>("Giới hạn dưới",
                    contestConfig.getDistanceLowerLimit(), (v) -> {
                if (v == null) {
                    return false;
                }
                contestConfig.setDistanceLowerLimit(v);
                yardConfig.update();
                return true;
            }));
            this.showRoom.addElement(new LineConfigSetting<>("Giới hạn trên",
                    contestConfig.getDistanceUpperLimit(), (v) -> {
                if (v == null) {
                    return false;
                }
                contestConfig.setDistanceUpperLimit(v);
                yardConfig.update();
                return true;
            }));
            this.showRoom.addElement(new LineConfigSetting<>("Số cảm biến",
                    contestConfig.getIndexOfYardInput(), (v) -> {
                if (v == null) {
                    return false;
                }
                contestConfig.setIndexOfYardInput(v);
                yardConfig.update();
                return true;
            }));
            this.showRoom.run();
        } else if (this.contestConfigs != null) {
            this.linesShowRoom = new EditElementShowRoom<>(YardContestConfigSetting.class,
                    null,
                    (t) -> {
                        if (t == null) {
                            return false;
                        }
                        t.close();
                        return true;
                    }, 2, 3);
            this.linesShowRoom.setAddAction(() -> {
                var config = new ContestConfig();
                this.contestConfigs.add(config);
                this.yardConfig.update();
                return new YardContestConfigSetting(String.valueOf(this.contestConfigs.size() - 1),
                        config);
            });
            this.linesShowRoom.setOkAction((t) -> {
                if (t == null) {
                    return false;
                }
                t.run();
                return false;
            });
            this.linesShowRoom.setDeleteAction((t) -> {
                if (t == null || t.contestConfig == null) {
                    return false;
                }
                int index = this.contestConfigs.indexOf(t.contestConfig);
                if (index == -1) {
                    return false;
                }
                this.contestConfigs.remove(index);
                this.yardConfig.update();
                return true;
            });
            this.linesShowRoom.setShowRoomName(getName());
            this.linesShowRoom.removeAllElement();
            for (int i = 0; i < contestConfigs.size(); i++) {
                this.linesShowRoom.addElement(new YardContestConfigSetting(
                        String.valueOf(i),
                        contestConfigs.get(i)));
            }
            this.linesShowRoom.run();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

}
