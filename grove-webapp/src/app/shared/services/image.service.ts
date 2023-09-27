import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Image} from '../../model/image.model';
import {BehaviorSubject, Observable} from 'rxjs';
import {environment} from '../../../environments/environment';
import {tap} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class ImageService {
  apiUri: string;
  private cachedImages: Image[] = [];
  imagesSubject$: BehaviorSubject<Image[]> = new BehaviorSubject<Image[]>([]);

  constructor(private http: HttpClient) {
    this.apiUri = environment.apiUri;
  }


  /* ============================================== REST API METHODS =================================================================== */
  fetchImage(id: number): Observable<Image> {
    return this.http
      .get(this.apiUri + '/api/admin/images/' + id, {responseType: 'json'});
  }

  fetchImages(gameId: number): Observable<Image[]> {
    return this.http.get<Image[]>(this.apiUri + '/api/admin/games/' + gameId + '/images', {responseType: 'json'})
      .pipe(tap(i => this.imagesSubject$.next(i)));

  }

  uploadImage(file: File, gameId: number): Observable<Image> {
    const fd: FormData = new FormData();
    const hd: HttpHeaders = new HttpHeaders();

    hd.append('Content-Type', undefined);

    fd.append('file', file);
    return this.http.post<Image>(this.apiUri + '/api/admin/images/games/' + gameId, fd, {headers: hd});
  }

  /* ================================================ OTHER METHODS ==================================================================== */

  updateImagesSubject(image: Image): void {

    this.cachedImages.push(image);
    console.table(this.cachedImages);
    this.imagesSubject$.next(this.cachedImages);
  }

  getImages(): Image[] {
    return this.cachedImages;
  }
}
