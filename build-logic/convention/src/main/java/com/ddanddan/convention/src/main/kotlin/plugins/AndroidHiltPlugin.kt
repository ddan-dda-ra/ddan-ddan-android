package plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Hilt 를 적용할 모듈에 사용할 플러그인
 *
 * plugin id : [ddanddan.android.androidHilt]
 */

//Plugin<Project> 인터페이스의 구현은 구현하는 클래스(플러그인)가 Gradle 프로젝트에 적용될 수 있는 플러그인임을 의미
//이 플러그인이 Gradle의 'Project'타입에 적용됨을 의미
class AndroidHiltPlugin : Plugin<Project> {
    override fun apply(target: Project) { //target: 이 플러그인이 적용되는 대상 Gradle 프로젝트
        with(target) {
            with(pluginManager) {//pluginManager: Gradle 프로젝트의 플러그인을 관리하는 역할을 하며 "dagger.hilt.android.plugin"이라는 플러그인을 프로젝트에 적용
                apply("dagger.hilt.android.plugin")
            }
        }
    }
}