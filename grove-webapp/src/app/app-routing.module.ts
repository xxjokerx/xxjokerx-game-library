import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MechanismsComponent} from './admin/dashboard/library/mechanism/mechanisms.component';
import {permissionGuard} from './permissionGuard';
import {MechanismEditComponent} from './admin/dashboard/library/mechanism/mechanism-edit/mechanism-edit.component';
import {
  MechanismDetailComponent
} from './admin/dashboard/library/mechanism/mechanism-detail/mechanism-detail.component';
import {MechanismResolver} from './admin/dashboard/library/mechanism/mechanism-resolver.service';
import {ConfigurationComponent} from './admin/dashboard/library/configuration/configuration.component';
import {CreatorsComponent} from './admin/dashboard/library/creators/creators.component';
import {CreatorEditComponent} from './admin/dashboard/library/creators/creator-edit/creator-edit.component';
import {CreatorDetailComponent} from './admin/dashboard/library/creators/creator-detail/creator-detail.component';
import {CreatorResolver} from './admin/dashboard/library/creators/creator-resolver.service';
import {PublishersComponent} from './admin/dashboard/library/publishers/publishers.component';
import {PublisherEditComponent} from './admin/dashboard/library/publishers/publisher-edit/publisher-edit.component';
import {
  PublisherDetailComponent
} from './admin/dashboard/library/publishers/publisher-detail/publisher-detail.component';
import {ExistingMechanismsResolver} from './admin/dashboard/library/mechanism/existing-mecanisms-resolver.service';
import {PublishersNamesResolver} from './admin/dashboard/library/publishers/publishers-names-resolver.service';
import {PublishersResolver} from './admin/dashboard/library/publishers/publishers-resolver.service';
import {CreatorNameResolver} from './admin/dashboard/library/creators/creator-name-resolver.service';
import {CategoriesComponent} from './admin/dashboard/library/categories/categories.component';
import {CategoryEditComponent} from './admin/dashboard/library/categories/category-edit/category-edit.component';
import {CategoryDetailComponent} from './admin/dashboard/library/categories/category-detail/category-detail.component';
import {CategoryResolver} from './admin/dashboard/library/categories/category-resolver.service';
import {GamesComponent} from './admin/dashboard/library/games/games.component';
import {GameOverviewResolver} from './admin/dashboard/library/games/game-overview-resolver.service';
import {GameSummaryComponent} from './admin/dashboard/library/games/game-list/game-summary/game-summary.component';
import {GameDetailComponent} from './admin/dashboard/library/games/game-detail/game-detail.component';
import {GameResolver} from './admin/dashboard/library/games/game-resolver.service';
import {GameEditComponent} from './admin/dashboard/library/games/game-edit/game-edit.component';
import {
  CategoryHandlerComponent
} from './admin/dashboard/library/games/game-edit/category-handler/category-handler.component';
import {TitleHandlerComponent} from './admin/dashboard/library/games/game-edit/title-handler/title-handler.component';
import {InfoHandlerComponent} from './admin/dashboard/library/games/game-edit/info-handler/info-handler.component';
import {
  CreatorHandlerComponent
} from './admin/dashboard/library/games/game-edit/creator-handler/creator-handler.component';
import {
  PublisherHandlerComponent
} from './admin/dashboard/library/games/game-edit/publisher-handler/publisher-handler.component';
import {SizeHandlerComponent} from './admin/dashboard/library/games/game-edit/size-handler/size-handler.component';
import {
  MaterialHandlerComponent
} from './admin/dashboard/library/games/game-edit/material-handler/material-handler.component';
import {ImageHandlerComponent} from './admin/dashboard/library/games/game-edit/image-handler/image-handler.component';
import {
  GameEditHelperComponent
} from './admin/dashboard/library/games/game-edit/game-edit-helper/game-edit-helper.component';
import {
  DescriptionHandlerComponent
} from './admin/dashboard/library/games/game-edit/description-handler/description-handler.component';
import {
  NewGameBasicsComponent
} from './admin/dashboard/library/games/new-game/new-game-basics/new-game-basics.component';
import {NewGameComponent} from './admin/dashboard/library/games/new-game/new-game.component';
import {HomeComponent} from './home/home.component';
import {
  MechanismHandlerComponent
} from './admin/dashboard/library/games/game-edit/mechanism-handler/mechanism-handler.component';
import {LoginComponent} from './auth/login/login.component';
import {ErrorPageComponent} from './error/error-page/error-page.component';
import {CopyHandlerComponent} from './admin/dashboard/library/games/game-edit/copy-handler/copy-handler.component';
import {RulesHandlerComponent} from './admin/dashboard/library/games/game-edit/rules-handler/rules-handler.component';
import {DashboardComponent} from './admin/dashboard/library/dashboard.component';
import {DashboardHomeComponent} from './admin/dashboard/dashobard-home/dashboard-home.component';
import {AdminWrapperComponent} from './admin/admin-wrapper/admin-wrapper.component';
import {LibHomeComponent} from './admin/dashboard/library/lib-home/lib-home.component';
import {WipComponent} from './shared/components/wip/wip.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'admin',
    component: AdminWrapperComponent,
    canActivate: [permissionGuard],
    children: [
      {
        path: '',
        component: DashboardHomeComponent,
      },
      {
        path: 'lib',
        component: DashboardComponent,
        children: [
          {
            path: '',
            component: LibHomeComponent
          },
          {
            path: 'games',
            component: GamesComponent,
            resolve: [GameOverviewResolver],
            children: [
              {
                path: ':id',
                component: GameSummaryComponent
              },
              {
                path: 'detail/:id',
                component: GameDetailComponent,
                resolve: [GameResolver]
              }
            ]
          },
          {
            path: 'games/new',
            component: NewGameComponent,
            children: [
              {
                path: 'basics',
                component: NewGameBasicsComponent
              }
            ]
          },
          {
            path: 'games/:id/edit',
            component: GameEditComponent,
            resolve: [GameResolver],
            children: [
              {
                path: '',
                component: GameEditHelperComponent,
              },
              {
                path: 'name',
                component: TitleHandlerComponent
              },

              {
                path: 'copy',
                children: [
                  {
                    path: 'new',
                    component: CopyHandlerComponent,
                  },
                  {
                    path: ':copyId',
                    component: CopyHandlerComponent,
                  }
                ]
              },
              {
                path: 'rules',
                component: RulesHandlerComponent,
              },
              {
                path: 'categories',
                component: CategoryHandlerComponent
              },
              {
                path: 'mechanisms',
                component: MechanismHandlerComponent
              },
              {
                path: 'infos',
                component: InfoHandlerComponent
              },
              {
                path: 'creators',
                component: CreatorHandlerComponent
              },
              {
                path: 'publisher',
                component: PublisherHandlerComponent
              },
              {
                path: 'description',
                component: DescriptionHandlerComponent
              },
              {
                path: 'size',
                component: SizeHandlerComponent
              },
              {
                path: 'material',
                component: MaterialHandlerComponent
              },
              {
                path: 'images',
                component: ImageHandlerComponent
              },
            ]
          },
          {
            path: 'mechanisms',
            component: MechanismsComponent,
            children: [
              {path: 'new', component: MechanismEditComponent, resolve: [ExistingMechanismsResolver]},
              {
                path: ':id/edit',
                component: MechanismEditComponent,
                resolve: [MechanismResolver, ExistingMechanismsResolver]
              },
              {path: ':id', component: MechanismDetailComponent, resolve: [MechanismResolver]}
            ]
          },
          {
            path: 'creators',
            component: CreatorsComponent,
            children: [
              {path: 'new', component: CreatorEditComponent, resolve: [CreatorNameResolver]},
              {
                path: ':id/edit',
                component: CreatorEditComponent,
                resolve: [CreatorResolver, CreatorNameResolver]
              },
              {path: ':id', component: CreatorDetailComponent, resolve: [CreatorResolver]}
            ]
          },
          {
            path: 'publishers',
            component: PublishersComponent,
            children: [
              {path: 'new', component: PublisherEditComponent, resolve: [PublishersNamesResolver]},
              {
                path: ':id/edit',
                component: PublisherEditComponent,
                resolve: [PublishersResolver, PublishersNamesResolver]
              },
              {path: ':id', component: PublisherDetailComponent, resolve: [PublishersResolver]}
            ]
          },
          {
            path: 'categories',
            component: CategoriesComponent,
            resolve: [CategoryResolver],
            children: [
              {path: 'new', component: CategoryEditComponent},
              {path: ':id/edit', component: CategoryEditComponent, resolve: [CategoryResolver]},
              {path: ':id', component: CategoryDetailComponent, resolve: [CategoryResolver]}
            ]
          },
          {
            path: 'configuration',
            component: ConfigurationComponent
          }]
      },
      {
        path: 'loans',
        component: WipComponent,
        children: [
          {
            path: 'select-member',
            component: WipComponent
          },
          {
            path: ':accountId/select-game',
            component: WipComponent
          },
          {
            path: 'confirm',
            component: WipComponent
          },
          {
            path: 'list',
            component: WipComponent
          },
          {
            path: ':id',
            component: WipComponent
          }
        ]
      },
      {
        path: 'members',
        component: WipComponent,
        children: [
          {
            path: 'list',
            component: WipComponent
          },
          {
            path: ':id',
            component: WipComponent
          }
        ]
      },
    ]
  },
  {
    path: 'search',
    component: WipComponent
  },
  {
    path: 'not-found',
    component: ErrorPageComponent,
    data: {message: 'page not found!'}
  },
  {
    path: '**',
    redirectTo: '/not-found'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes
    , {enableTracing: false})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {

}
