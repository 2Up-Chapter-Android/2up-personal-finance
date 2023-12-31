//
//  AppDelegate.swift
//  iosApp
//
//  Created by Ethan Nguyen on 11/04/2023.
//  Copyright © 2023 orgName. All rights reserved.
//
import UIKit
import Foundation
import shared

class AppDelegate: NSObject, UIApplicationDelegate {
    // Lazy so it doesn't try to initialize before startKoin() is called
//    lazy var log = koin.get(objCClass: Logger.self, parameter: "AppDelegate") as! Logger
//    lazy var analytics = koin.get(objCProtocol: AnalyticsService.self, qualifier: nil) as! AnalyticsService
//    lazy var appChecker = koin.get(objCClass: AppChecker.self) as! AppChecker

    func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
//        FirebaseApp.configure()
//        AppInitKt.setupKermit()
        
        startKoin()
        
//        try! appChecker.checkTimeZoneHash()

//        analytics.logEvent(name: AnalyticsServiceCompanion().EVENT_STARTED, params: [:])

//        log.v(message: { "App Started" })
        return true
    }
}
